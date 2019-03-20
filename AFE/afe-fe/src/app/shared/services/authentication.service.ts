import {Injectable} from '@angular/core';
import {LoginForm, RegistrationForm} from '../models';
import {Observable} from 'rxjs/Observable';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {
  DICTIONARY_CURRENT_TIME_URL, LOGIN_URL, LOGOUT_URL, REFRESH_TOKEN_URL, REGISTRATION_URL,
  USER_PERMISSIONS_URL, USER_REFRESH_TOKEN_GET_URL
} from '../shared.constants';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/of';
import 'rxjs/add/observable/throw';
import {Subject} from 'rxjs/Subject';
import {AuthService} from 'ngx-auth';


export class ServerTime {
  public static serverTime: Date = new Date();
  public static fetchesTime: Date = new Date();

  public static setStartTime(baseDate) {
    this.serverTime = baseDate;
    this.fetchesTime = new Date();
  }

  public static getCurrent() {
    const now = new Date();
    const offset = now.getTime() - this.fetchesTime.getTime();
    this.serverTime.setTime(this.serverTime.getTime() + offset);
    this.fetchesTime = now;
    return this.serverTime;
  }
}

@Injectable()
export class AuthenticationService implements AuthService {

  private _authorized: boolean;
  private _logoutSubject: Subject<boolean> = new Subject();
  public onLogout: Observable<boolean> = this._logoutSubject.asObservable();

  private _loginSubject: Subject<boolean> = new Subject();
  public onLogin: Observable<boolean> = this._loginSubject.asObservable();

  constructor(private http: HttpClient) {
    this._authorized = null;
  }

  public loadTime() {
    this.http.get<{ currentTime: string }>(DICTIONARY_CURRENT_TIME_URL)
      .map(m => new Date(m.currentTime))
      .subscribe(i => ServerTime.setStartTime(i));
  }

  public isAuthorized(): Observable<boolean> {
    if (this._authorized === null) {
      return this.hasSession();
    }
    return Observable.of(this._authorized);
  }

  public hasSession() {
    return this.http.get<boolean>(USER_REFRESH_TOKEN_GET_URL)
      .map(res => {
        this._authorized = res;
        return res;
      })
      .catch((err) => {
        this._authorized = false;
        return Observable.of(false);
      });
  }

  public login(req: LoginForm): Observable<any> {
    req.email = req.email.trim().toLocaleLowerCase();
    return this.http.post<any>(LOGIN_URL, req)
      .map(res => {
        this._authorized = true;
        this._loginSubject.next(true);
        return res;
      })
      .catch((err) => {
        this._authorized = false;
        return Observable.throw(err.error);
      });
  }

  public logout(): Observable<boolean> {
    return this.http.post<any>(LOGOUT_URL, {})
      .map(res => {
        this._authorized = false;
        this._logoutSubject.next(true);
        return true;
      })
      .catch((err) => {
        this._authorized = false;
        return Observable.throw(err);
      });
  }

  get authorized() {
    return this._authorized;
  }


  registration(req: RegistrationForm) {
    return this.http.put<any>(REGISTRATION_URL, req)
      .map(res => {
        return res;
      })
      .catch((err) => {
        return Observable.throw(err.error);
      });
  }

  getAccessToken(): Observable<string> {
    return Observable.of(this._authorized + '');
  }

  public refreshToken() {
    return this.http.get<boolean>(USER_REFRESH_TOKEN_GET_URL)
      .map(res => {
        if (res === false) {
          throw new Error('User become unauthorized');
        }
        return res;
      })
      .catch((err) => {
        location.reload(true);
        return Observable.throw(err);
      });
  }

  getHeaders(token: string): { [p: string]: string | string[] } {
    return undefined;
  }

  refreshShouldHappen(response: HttpErrorResponse): boolean {
    return response.status === 401;
  }

  verifyTokenRequest(url: string): boolean {
    return url.endsWith('refresh-token');
  }


}
