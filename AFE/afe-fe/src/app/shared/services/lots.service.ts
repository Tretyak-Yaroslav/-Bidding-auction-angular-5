import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Product} from '../models';
import {HttpClient} from '@angular/common/http';
import {
  LOTS_URL,
  PRODUCTS_ALL_GET_STATUS_URL,
  PRODUCTS_ALL_GET_URL,
  PRODUCTS_CURRENT_POST_URL,
  PRODUCTS_GET_URL,
  PRODUCTS_PHOTO_GET_URL,
  PRODUCTS_SORT_LIMIT_POST_URL
} from '../shared.constants';
import {ProductStatus, ProductSortType} from '../models';

@Injectable()
export class LotsService {

  constructor(private http: HttpClient) {

  }

  getProduct(id: number) {
    return this.http.get<{ product: Product }>(PRODUCTS_GET_URL + id)
      .map(i => {
        prepareProduct(i.product);
        return i.product;
      });
  }

  getAllProducts(): Observable<Product[]> {
    return this.http.get<{ items: Product[] }>(PRODUCTS_ALL_GET_URL)
      .map(res => {
        res.items.forEach(i => prepareProduct(i));
        return res.items;
      })
      .catch((err) => {
        return Observable.throw(err);
      });
  }


  getRange(limit: number, sortType: ProductSortType) {
    return this.http.post<{ items: Product[] }>(PRODUCTS_SORT_LIMIT_POST_URL, {sortType, limit})
      .map(res => {
        res.items.forEach(i => prepareProduct(i));
        return res.items;
      })
      .catch((err) => {
        return Observable.throw(err);
      });
  }

  getProductsByStatusOfCurrentClient(statuses: ProductStatus[]) {
    return this.http.post<{ items: Product[] }>(PRODUCTS_CURRENT_POST_URL, {statuses})
      .map(res => {
        res.items.forEach(i => prepareProduct(i));
        return res.items;
      })
      .catch((err) => {
        return Observable.throw(err);
      });
  }

  getProductsByStatus(statuses: ProductStatus[]) {
    return this.http.post<{ items: Product[] }>(PRODUCTS_ALL_GET_STATUS_URL, {statuses})
      .map(res => {
        res.items.forEach(i => prepareProduct(i));
        return res.items;
      })
      .catch((err) => {
        return Observable.throw(err);
      });
  }
}

export function prepareProduct(product: Product) {
  setDate(product);
  if (product.photo === null) {
    product.photoSrc = './assets/images/product_default.png';
  } else {
    product.photoSrc = PRODUCTS_PHOTO_GET_URL + product.productId;
  }
  return product;
}

export function setDate(i: Product) {
  i.createdDatetime = new Date(i.createdDatetime);
  i.endDate = new Date(i.endDate);
  i.startDate = new Date(i.startDate);
  return i;
}
