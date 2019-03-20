import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Dictionary} from './create-product.interfaces';
import {DICTIONARY_GET_URL, PRODUCT_IMAGE_PUT_URL, PRODUCT_REGISTER_POST_URL} from './create-product.contstances';
import {Product} from '../../shared/models';

@Injectable()
export class CreateProductService {

  constructor(private http: HttpClient) {
  }

  public getDictionary(): Observable<Dictionary[]> {
    return this.http.get<{ items: Dictionary[] }>(DICTIONARY_GET_URL)
      .map(i => i.items);
  }

  public createProduct(product: any): Observable<Product> {
    product.startDate = product.startDate.toISOString();
    product.endDate = product.endDate.toISOString();
    product.photo = undefined;
    return this.http.post<{ product: Product }>(PRODUCT_REGISTER_POST_URL, product)
      .map(i => i.product)
      .catch(e => Observable.throw(e.error));
  }

  public updatePhoto(file, id) {
    const headers = new HttpHeaders();
    headers.append('Content-Type', undefined);
    return this.http.put(PRODUCT_IMAGE_PUT_URL + id, file, {headers}).catch(e => Observable.throw(e.error));
  }


}
