import {Injectable} from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {AuthenticationService} from '../services/authentication.service';

@Injectable()
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthenticationService,
              private router: Router) {
  }

  canActivate(next: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return this.authService.isAuthorized().map(
      r => {
        if (r === false) {
          this.router.navigate(['/login'], {
            queryParams: {
              returnUrl: state.url
            }
          });
        }
        return !!r;
      },
      (err) => {
        this.router.navigate(['/login'], {
          queryParams: {
            returnUrl: state.url
          }
        });
        return false;
      }
    );
  }
}
