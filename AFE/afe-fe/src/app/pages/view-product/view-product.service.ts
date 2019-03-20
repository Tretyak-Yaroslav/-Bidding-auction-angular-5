import {Injectable} from '@angular/core';
import {AUCTION_MAKE_BID_PUT_URL, AUCTION_BUYOUT_GET_URL} from './view-product.constants';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class ViewProductService {


  constructor(private http: HttpClient) {
  }

  makeBid(productId: number, amount: number) {
    return this.http.put(AUCTION_MAKE_BID_PUT_URL, {amount, productId});
  }

  buyOutProduct(productId: number) {
    return this.http.post(AUCTION_BUYOUT_GET_URL, {productId});
  }
}
