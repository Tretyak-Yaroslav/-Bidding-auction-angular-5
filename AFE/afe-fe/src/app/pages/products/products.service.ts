import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {PRODUCT_APPROVE_DEAL_POST_URL, PRODUCT_APPROVE_POST_URL, PRODUCT_REJECT_POST_URL} from './products.contants';

@Injectable()
export class ProductsService {

  constructor(private http: HttpClient) {
  }

  public rejectProduct(id: number): Observable<any> {
    return this.http.post(PRODUCT_REJECT_POST_URL + id, {});
  }

  public approveProduct(id: number): Observable<any> {
    return this.http.post(PRODUCT_APPROVE_POST_URL + id, {});
  }

  public rejectSale(id: number): Observable<any> {
    return this.http.post('' + id, {});
  }

  public approveSale(id: number): Observable<any> {
    return this.http.post(PRODUCT_APPROVE_DEAL_POST_URL + id, {});
  }

}
