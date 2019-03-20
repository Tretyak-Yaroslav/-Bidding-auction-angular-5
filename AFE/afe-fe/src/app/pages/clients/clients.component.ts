import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Client, ClientStatus, SortDirection} from '../../shared/models';
import {FormBuilder} from '@angular/forms';
import {ClientsService} from './clients.service';
import {IAlertButton, IAlertRequest} from '../../modules/alert/alert.interfaces';
import {AlertService} from '../../modules/alert/alert.service';
import {SortFilter} from '../../components/sort-filter';
import {USER_AVATAR_GET_URL} from '../../shared/shared.constants';


export enum ClientGroup {
  Approve = 2,
  Active = 4,
  Hidden = 8
}

export const BUTTONS = {
  hide: Object.assign(new IAlertButton(), {classes: 'btn btn-danger', isConfirm: true, title: 'Disabled client'}),
  cancel: Object.assign(new IAlertButton(), {classes: 'btn btn-link', isConfirm: false, title: 'Cancel'}),
  unhide: Object.assign(new IAlertButton(), {
    classes: 'btn btn-primary',
    isConfirm: true,
    title: 'Enable client'
  }),
  approve: Object.assign(new IAlertButton(), {
    classes: 'btn btn-primary',
    isConfirm: true,
    title: 'Approve client'
  })
};

@Component({
  selector: 'afe-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.scss']
})
export class ClientsComponent extends SortFilter implements OnInit {

  SortDirection = SortDirection;
  ClientGroup = ClientGroup;
  USER_AVATAR_GET_URL = USER_AVATAR_GET_URL;

  public _pureClient: Client[];
  public _approveClient: Client[];
  public _activeClient: Client[];
  public _hiddenClient: Client[];

  public activeGroup: ClientGroup;
  public activeRatingRisk: boolean;
  public activeClient: Client;

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private alertService: AlertService,
              private route: ActivatedRoute,
              private clientsService: ClientsService,
              private changeDetectorRef: ChangeDetectorRef) {
    super(formBuilder);
    this.searchBy = 'name';
    this._activeClient = null;
    this._hiddenClient = null;
    this.activeGroup = ClientGroup.Active;
    this.activeRatingRisk = false;
    this.activeClient = null;
  }

  ngOnInit() {
    this.clientsService.getAllClients().subscribe(clients => {
      this.loadClients(clients);
    });
  }

  public loadClients(clients: Client[]) {
    this._pureClient = clients;
    this._activeClient = clients.filter(client => !client.hidden && client.status == ClientStatus.ACTIVE);
    this._hiddenClient = clients.filter(client => client.hidden && client.status == ClientStatus.ACTIVE);
    this._approveClient = clients.filter(client => client.status == ClientStatus.PENDING);
    this.setActiveGroup(this.activeGroup);
  }

  public setActiveGroup(group: ClientGroup) {
    this.activeGroup = group;
    switch (this.activeGroup) {
      case        ClientGroup.Approve: {
        this._allItems = this._approveClient;
        break;
      }
      case        ClientGroup.Active: {
        this._allItems = this._activeClient;
        break;
      }
      case        ClientGroup.Hidden: {
        this._allItems = this._hiddenClient;
        break;
      }
    }
    this.search();
  }

  hideClient(client: Client) {
    const req: IAlertRequest = {
      caption: 'Hide client',
      text: 'You going to hide client. The user will not able to enter to his profile, make bid, view products etc.',
      alertButtons: [BUTTONS.hide, BUTTONS.cancel]
    };
    this.alertService.create(req).subscribe(res => {
      if (res.confirm) {
        client._loading = true;
        this.clientsService.hideClient(client.clientId).subscribe((e) => {
          client._loading = false;
          client.hidden = true;
          this.loadClients(this._pureClient);
        }, e => {
          client._loading = false;
        });
      }
    });
  }

  rebornClient(client: Client) {
    const req: IAlertRequest = {
      caption: 'Enable client',
      text: 'You going to enable client profile',
      alertButtons: [BUTTONS.unhide, BUTTONS.cancel]
    };
    this.alertService.create(req).subscribe(res => {
      if (res.confirm) {
        client._loading = true;
        this.clientsService.unhideClient(client.clientId).subscribe((e) => {
          client._loading = false;
          client.hidden = false;
          this.loadClients(this._pureClient);
        }, e => {
          client._loading = false;
        })
        ;
      }
    });
  }

  activateClient(client: Client) {
    const req: IAlertRequest = {
      caption: 'Approve client',
      text: 'You going to approve new client',
      alertButtons: [BUTTONS.approve, BUTTONS.cancel]
    };
    this.alertService.create(req).subscribe(res => {
      if (res.confirm) {
        client._loading = true;
        this.clientsService.approveClient(client.clientId).subscribe((e) => {
          client._loading = false;
          client.status = ClientStatus.ACTIVE;
          this.loadClients(this._pureClient);
        }, e => {
          client._loading = false;
        })
        ;
      }
    });
  }

  showRiskRating(client: Client) {
    this.activeRatingRisk = true;
    this.activeClient = client;
  }

  onRiskRatingHide(confirm) {
    this.activeRatingRisk = false;
  }


}
