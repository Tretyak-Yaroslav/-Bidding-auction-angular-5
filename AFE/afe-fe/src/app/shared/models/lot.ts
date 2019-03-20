import {ILoading} from './iloading';

export enum ProductStatus {
  PENDING = 'PENDING', // aproduc
  REJECTED = 'REJECTED',
  OWNED = 'OWNED',
  FOR_SALE = 'FOR_SALE',// buyout? make bids ----
  SALE_PENDING = 'SALE_PENDING',//
  SOLD = 'SOLD',
  EXPIRED = 'EXPIRED'
}

export class Product implements ILoading {
  _loading: boolean;
  buyOutPrice: number;
  createdDatetime: Date;
  description: string;
  endDate: Date;
  name: string;
  price: number;
  photo: string;
  photoSrc?: string;
  platformId: number;
  productId: number;
  productOwnerId: number;
  productStatus: ProductStatus;
  riskRanking: number;
  startDate: Date;
  startingBid: number;
  bidCount: number;
  ProductStatus: number;
}
