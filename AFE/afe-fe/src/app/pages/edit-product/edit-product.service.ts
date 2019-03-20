import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {DICTIONARY_GET_URL} from '../create-product/create-product.contstances';
import {Dictionary} from '../create-product/create-product.interfaces';
import {HttpClient} from '@angular/common/http';
import {PRODUCT_UPDATE_POST_URL} from './edit-product.constants';

@Injectable()
export class EditProductService {


  constructor(private http: HttpClient) {
  }

  public getDictionary(): Observable<Dictionary[]> {
    return this.http.get<{ items: Dictionary[] }>(DICTIONARY_GET_URL)
      .map(i => i.items);
  }

  public updateProduct(product: any) {
    return this.http.post(PRODUCT_UPDATE_POST_URL, product);
  }

}
