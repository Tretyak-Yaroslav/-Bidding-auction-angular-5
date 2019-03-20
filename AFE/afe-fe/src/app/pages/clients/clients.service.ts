import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';
import {
  CLIENT_APPROVE_POST_URL,
  CLIENT_HIDE_POST_URL, CLIENT_SET_RISK_POST_URL, CLIENT_UNHIDE_POST_URL,
  CLIENTS_ALL_GET_URL, QUESTIONNAIRE_GET_URL
} from './clients.constants';
import {Questionnaire} from '../questionnaire/questionnaire.interfaces';
import {Client} from '../../shared/models';
import {USER_AVATAR_GET_URL} from '../../shared/shared.constants';

@Injectable()
export class ClientsService {

  constructor(private http: HttpClient) {
  }

  public getAllClients(): Observable<Client[]> {
    return this.http.get<{ items: Client[] }>(CLIENTS_ALL_GET_URL)
      .map(i => {
        i.items.forEach(c => {
          if (c.avatar === null) {
            c.avatarSrc = './assets/images/default_avatar.png';
          } else {
            c.avatarSrc = USER_AVATAR_GET_URL + c.clientId;
          }
        });
        return i.items;
      });
  }

  public hideClient(id: number): Observable<any> {
    return this.http.post(CLIENT_HIDE_POST_URL + id, {});
  }

  public unhideClient(id: number): Observable<any> {
    return this.http.post(CLIENT_UNHIDE_POST_URL + id, {});
  }

  public setClientRisk(clientId: number, atr: number) {
    return this.http.post(CLIENT_SET_RISK_POST_URL, {atr, clientId});
  }

  public approveClient(clientId: number) {
    return this.http.post(CLIENT_APPROVE_POST_URL + clientId, {});
  }

  getQuestionnaire(id: number): Observable<Questionnaire> {
    return this.http.get<{ questionnaire: Questionnaire }>(QUESTIONNAIRE_GET_URL + id).map(i => i.questionnaire);
  }
}
