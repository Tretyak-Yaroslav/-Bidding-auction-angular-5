import {
  ChangeDetectionStrategy, ChangeDetectorRef,
  Component,
  EventEmitter,
  HostBinding,
  Input,
  OnChanges,
  OnInit,
  Output
} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {Client, Product, ProductStatus} from '../../shared/models';
import {BidsService} from './bids.service';
import {Bid, BidStatusType} from './bids.interfaces';
import {UserService} from '../../shared/services/user.service';
import {ServerTime} from '../../shared/services/authentication.service';


@Component({
  selector: 'afe-bids',
  templateUrl: './bids.component.html',
  styleUrls: ['./bids.component.scss'],
  // changeDetection: ChangeDetectionStrategy.OnPush
})
export class BidsComponent implements OnInit, OnChanges {
  BidStatusType = BidStatusType;
  ProductStatus = ProductStatus;
  @Input() product: Product;

  // tslint:disable-next-line
  @Output('onClose') public onClose: EventEmitter<boolean>;
  @HostBinding('class.disabled') public disabled: boolean;
  public appear: boolean;
  public loading: boolean;
  public disappear: boolean;
  public bids: Bid[];
  public countBiders: number;
  public client: Client;
  public selectBit: boolean;
  public disabledChoose: boolean;

  public currentTime: Date;


  constructor(private bidsService: BidsService,
              private userService: UserService,
             // private changeDetectorRef: ChangeDetectorRef,
              private formBuilder: FormBuilder) {
    this.product = new Product();
    this.onClose = new EventEmitter();

    this.countBiders = 0;
    this.loading = false;
    this.appear = true;
    this.disappear = false;
    this.selectBit = false;
    this.disabledChoose = false;
    this.currentTime = ServerTime.getCurrent();
    this.disabled = false;
    this.bids = null;
  }

  ngOnInit() {
    this.userService.getCurrentClient().subscribe(i => {
      this.client = i;
      this.checkOwnerRights();
    });
  }

  ngOnChanges(ch) {
    if (ch.product) {
      this.loadBids();
      this.checkOwnerRights();
    }

    if (ch.owner) {
      this.checkOwnerRights();
    }
  }

  loadBids() {
    if (this.product.productId) {
      this.loading = true;
      this.bidsService.getBids(this.product.productId).subscribe(quest => {
        this.bids = quest.sort((x, y) => y.amount - x.amount);
        this.countBiders = quest.filter(function (x, i, a) {
          return quest.findIndex(xi => xi.clientId == x.clientId) === i;
        }).length;
        this.loading = false;
      });
    }
  }

  checkOwnerRights() {
    if (this.client && this.client.clientId === this.product.productOwnerId) {
      this.selectBit = true;
    }
  }

  show() {
    this.appear = true;
    this.disappear = false;
    this.disabled = false;
  }

  cancel(confirm = false) {
    this.appear = false;
    this.disappear = true;
    this.disabled = true;
    this.onClose.emit(confirm);
  }

  submitBid(id) {
    this.disabledChoose = true;
    this.bidsService.submitBid(id).subscribe(i => {
      this.selectBit = false;
      this.product.productStatus = ProductStatus.SALE_PENDING;
    });
  }

  cancelBid(id) {
    this.bidsService.cancelBid(id).subscribe(i => {
      this.loadBids();
    });
  }

  /*  saveRisk() {
      this.client.atr = this.riskForm.getRawValue().risk;
      this.clientsService.setClientRisk(this.client.clientId, this.client.atr).subscribe();
      this.cancel(true);
    }*/

}
