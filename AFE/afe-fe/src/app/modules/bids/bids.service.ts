import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {BIDS_ACCEPT_POST_URL, BIDS_CANCEL_POST_URL, BIDS_GET_URL} from './bids.constants';
import {Bid} from './bids.interfaces';

@Injectable()
export class BidsService {


  constructor(private http: HttpClient) {
  }

  public getBids(productId: number): Observable<Bid[]> {
    return this.http.get<{ items: any[] }>(BIDS_GET_URL + productId).map(i => i.items);
  }

  public submitBid(productId: number): Observable<any> {
    return this.http.post<any>(BIDS_ACCEPT_POST_URL + productId, {});
  }

  public cancelBid(productId: number): Observable<any> {
    return this.http.post<any>(BIDS_CANCEL_POST_URL + productId, {});
  }
}
