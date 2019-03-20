import {Injectable} from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {Permission, UserService} from '../services/user.service';

@Injectable()
export class ManagerGuard implements CanActivate {

  constructor(private userService: UserService) {
  }

  canActivate(next: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return this.userService.getPermissions()
      .map(perms => {
        return this.userService.hasPermission(Permission._IS_BACK_OFFICE, perms);
      }, e => false);
  }
}
