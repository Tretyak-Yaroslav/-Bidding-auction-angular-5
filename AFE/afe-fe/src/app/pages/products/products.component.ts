import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {LotsService} from '../../shared/services/lots.service';
import {Product, ProductStatus, SortDirection} from '../../shared/models';
import {FormBuilder} from '@angular/forms';
import {SortFilter} from '../../components/sort-filter';
import {IAlertRequest} from '../../modules/alert/alert.interfaces';
import {AlertService} from '../../modules/alert/alert.service';
import {ProductsService} from './products.service';
import {BUTTONS} from './products.contants';
import {PRODUCTS_PHOTO_GET_URL} from '../../shared/shared.constants';

export enum ProductGroup {
  PENDING,
  SALE,
  REJECTED
}

@Component({
  selector: 'afe-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent extends SortFilter implements OnInit {
  ProductGroup = ProductGroup;
  ProductStatus = ProductStatus;
  SortDirection = SortDirection;
  PRODUCTS_PHOTO_GET_URL = PRODUCTS_PHOTO_GET_URL;

  public activeGroup: ProductGroup;
  public activeProduct: Product;
  public activeBids: boolean;

  public _pureProducts: Product[];
  public _pandingProducts: Product[];
  public _saleProducts: Product[];

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private alertService: AlertService,
              private productsService: ProductsService,
              private route: ActivatedRoute,
              private lotsService: LotsService,
              private changeDetectorRef: ChangeDetectorRef) {
    super(formBuilder);
    this.searchBy = 'name';
    this.activeBids = false;
    this.activeGroup = ProductGroup.PENDING;
  }

  ngOnInit() {
    this.lotsService.getProductsByStatus([ProductStatus.PENDING]).subscribe(lots => {
      this._pandingProducts = lots;
      this.setActiveGroup(this.activeGroup);

    });
    this.lotsService.getProductsByStatus([ProductStatus.SALE_PENDING]).subscribe(lots => {
      this._saleProducts = lots;
      this.setActiveGroup(this.activeGroup);
    });
  }

  setActiveGroup(group: ProductGroup) {
    this.activeGroup = group;
    switch (this.activeGroup) {
      case        ProductGroup.PENDING: {
        this._allItems = this._pandingProducts;
        break;
      }
      case        ProductGroup.SALE: {
        this._allItems = this._saleProducts;
        break;
      }
    }
    this.search();
  }


  approveProduct(product: Product) {
    const req: IAlertRequest = {
      caption: 'Approve product',
      text: 'You are to accept product to be published. Are you sure you want to publish it?',
      alertButtons: [BUTTONS.apply, BUTTONS.cancelReject]
    };
    this.alertService.create(req).subscribe(res => {
      if (res.confirm) {
        product._loading = true;
        this.productsService.approveProduct(product.productId).subscribe((e) => {
          product._loading = false;
          product.productStatus = ProductStatus.FOR_SALE;
          this._pandingProducts.slice(this._pandingProducts.indexOf(product), 1);
        }, e => {
          product._loading = false;
        });
      }
    });
  }

  rejectProduct(product: Product) {
    const req: IAlertRequest = {
      caption: 'Reject item',
      text: 'You goint to reject item',
      alertButtons: [BUTTONS.applyReject, BUTTONS.cancelReject]
    };
    this.alertService.create(req).subscribe(res => {
      if (res.confirm) {
        product._loading = true;
        this.productsService.rejectProduct(product.productId).subscribe((e) => {
          product._loading = false;
          product.productStatus = ProductStatus.REJECTED;
          this._pandingProducts.slice(this._pandingProducts.indexOf(product), 1);
        }, e => {
          product._loading = false;
        });
      }
    });
  }

  showBids(product: Product) {
    this.activeBids = true;
    this.activeProduct = product;
  }

  onBidsHide(confirm) {
    this.activeBids = false;
  }

  approveSale(product: Product) {
    const req: IAlertRequest = {
      caption: 'Approve sale',
      text: 'You are goint to accept deal',
      alertButtons: [BUTTONS.apply, BUTTONS.cancelReject]
    };
    this.alertService.create(req).subscribe(res => {
      if (res.confirm) {
        product._loading = true;
        this.productsService.approveSale(product.productId).subscribe((e) => {
          product._loading = false;
          product.productStatus = ProductStatus.OWNED;
          this._saleProducts.slice(this._pandingProducts.indexOf(product), 1);
        }, e => {
          product._loading = false;
        });
      }
    });
  }

  rejectSale(product: Product) {
    const req: IAlertRequest = {
      caption: 'Reject item',
      text: 'You goint to reject item',
      alertButtons: [BUTTONS.applyReject, BUTTONS.cancelReject]
    };
    this.alertService.create(req).subscribe(res => {
      if (res.confirm) {
        product._loading = true;
        this.productsService.rejectSale(product.productId).subscribe((e) => {
          product._loading = false;
          product.productStatus = ProductStatus.REJECTED;
          this._pandingProducts.slice(this._pandingProducts.indexOf(product), 1);
        }, e => {
          product._loading = false;
        });
      }
    });
  }
}
