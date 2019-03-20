import {Component, OnInit} from '@angular/core';
import {Permission, UserService} from '../../shared/services/user.service';
import {AuthenticationService} from '../../shared/services/authentication.service';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Client} from '../../shared/models';
import {USER_AVATAR_GET_URL} from '../../shared/shared.constants';

@Component({
  selector: 'afe-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  USER_AVATAR_GET_URL = USER_AVATAR_GET_URL;

  public showSidebar: boolean;
  public managerPermission: boolean;
  public authorized: boolean;
  public searchForm: FormGroup;
  public client: Client;

  constructor(private userService: UserService,
              private router: Router,
              private formBuilder: FormBuilder,
              private authService: AuthenticationService) {
    this.managerPermission = false;
    this.authorized = false;
    this.showSidebar = false;
    this.client = new Client();
    this.searchForm = this.formBuilder.group({
      search: ['', Validators.required],
    });
  }

  ngOnInit() {
    this.router.events.subscribe((val) => {
      this.showSidebar = false;
    });
    this.authService.isAuthorized().subscribe(auth => {
      if (auth) {
        this.authorized = true;
        this.userService.getPermissions().subscribe(perms => {
          this.managerPermission = this.userService.hasPermission(Permission._IS_BACK_OFFICE, perms);
        });
        this.userService.getCurrentClient().subscribe(client => this.client = client);
      }
    }, e => e);

    this.authService.onLogout.subscribe((auth) => {
      this.authorized = false;
      this.managerPermission = false;
    });

    this.authService.onLogin.subscribe((auth) => {
      this.authorized = true;

      this.userService.getCurrentClient().subscribe(client => this.client = client, e => e);

      this.userService.getPermissions().subscribe(perms => {
        this.managerPermission = this.userService.hasPermission(Permission._IS_BACK_OFFICE, perms);
      }, e => e);
    }, e => e);

  }

  search() {
    this.router.navigate(['/search'], {
      queryParams: {
        text: this.searchForm.controls['search'].value
      }
    });
    this.searchForm.controls['search'].setValue('');
  }

  logout() {
    this.authService.logout().subscribe(
      i => {
        this.router.navigate(['/login']);
      },
      e => {
        this.router.navigate(['/login']);
      });
  }

}
