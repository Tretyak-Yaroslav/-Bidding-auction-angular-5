import {Injectable, OnInit} from '@angular/core';
import {
  USER_AVATAR_GET_URL,
  USER_CURRENT_URL, USER_PERMISSIONS_URL, USER_UPDATE_AVATAR_POST_URL, USER_UPDATE_INFO_URL,
  USER_UPDATE_PASSWORD_POST_URL
} from '../shared.constants';
import {Observable} from 'rxjs/Observable';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Client} from '../models';
import {AuthenticationService} from './authentication.service';

export enum Permission {
  TRUE = 'TRUE',
  _IS_BACK_OFFICE = '_IS_BACK_OFFICE',
  CLIENT_SHOW_RISK_RANKING_ATR = 'CLIENT_SHOW_RISK_RANKING_ATR'
}

export class UpdateUserInfoReq {
  birthday: string;
  name: string;
  phoneNumber: string;
}

export class UpdatePasswordReq {
  currentPassword: string;
  newPassword: string;
}


@Injectable()
export class UserService {

  private _permissionCache: Permission[];

  constructor(private http: HttpClient, private authService: AuthenticationService) {
    this._permissionCache = null;
    // TODO: provide unsubscribe
    this.authService.onLogout.subscribe(i => {
      this._permissionCache = null;
    });
  }

  public updatePassword(req: UpdatePasswordReq) {
    return this.http.post(USER_UPDATE_PASSWORD_POST_URL, req)
      .catch(i => Observable.throw(i.error));
  }

  public updateInfo(req: UpdateUserInfoReq) {
    return this.http.post(USER_UPDATE_INFO_URL, req)
      .catch(i => Observable.throw(i.error));
  }

  public updateAvatar(file) {
    const headers = new HttpHeaders();
    headers.append('Content-Type', undefined);
    return this.http.post(USER_UPDATE_AVATAR_POST_URL, file, {headers: headers})
      .catch(i => Observable.throw(i.error));
  }

  public getCurrentClient(): Observable<Client> {
    if (this.authService.authorized === false) {
      return Observable.throw({type: ''});
    }
    return this.http.get<{ client: Client }>(USER_CURRENT_URL)
      .map(res => {
        if (res.client.birthday) {
          res.client.birthday = new Date(res.client.birthday as string);
        }
        if (res.client.avatar === null) {
          res.client.avatarSrc = './assets/images/default_avatar.png';
        } else {
          res.client.avatarSrc = USER_AVATAR_GET_URL + res.client.clientId;
        }
        return res.client;
      })
      .catch((err) => {
        return Observable.throw(err);
      });
  }

  public getPermissions(): Observable<Permission[]> {
    if (this._permissionCache) {
      return Observable.of(this._permissionCache);
    }
    return this.http.get<{ roles: Permission[] }>(USER_PERMISSIONS_URL)
      .map(res => {
        this._permissionCache = res.roles;
        return res.roles;
      })
      .catch((err) => {
        return Observable.throw(err);
      });
  }

  hasPermission(permission: Permission, permissions: Permission[]): boolean {
    if (permission === Permission.TRUE) {
      return true;
    }
    let can = false;
    if (permissions) {
      permissions.forEach((dd) => {
        // tslint:disable-next-line
        if (permission == dd) {
          can = true;
        }
      });
    }
    return can;
  }
}
