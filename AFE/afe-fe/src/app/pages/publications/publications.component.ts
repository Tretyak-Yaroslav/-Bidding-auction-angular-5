import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder} from '@angular/forms';
import {LotsService} from '../../shared/services/lots.service';
import {Client, Product, ProductStatus, SortDirection} from '../../shared/models';
import {PageChangedEvent} from 'ngx-bootstrap';
import {AlertService} from '../../modules/alert/alert.service';
import {SortFilter} from '../../components/sort-filter';
import {UserService} from '../../shared/services/user.service';
import {PRODUCTS_PHOTO_GET_URL} from '../../shared/shared.constants';

export enum ProductGroup {
  FOR_SALE = 'FOR_SALE',
  PENDING = 'PENDING',
  SALE_PENDING = 'SALE_PENDING'
}

@Component({
  selector: 'afe-publications',
  templateUrl: './publications.component.html',
  styleUrls: ['./publications.component.scss']
})
export class PublicationsComponent extends SortFilter implements OnInit {

  ProductGroup = ProductGroup;
  ProductStatus = ProductStatus;
  SortDirection = SortDirection;
  PRODUCTS_PHOTO_GET_URL = PRODUCTS_PHOTO_GET_URL;

  public activeGroup: ProductGroup; // TODO: make static

  public client: Client;
  public _pureForSaleProducts: Product[];
  public _purePendingProducts: Product[];
  public _pureSalePendingProducts: Product[];

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private alertService: AlertService,
              private route: ActivatedRoute,
              private userService: UserService,
              private lotsService: LotsService,
              private changeDetectorRef: ChangeDetectorRef) {
    super(formBuilder);
    this.searchBy = 'name';
    this.activeGroup = ProductGroup.FOR_SALE;
  }

  ngOnInit() {
    const forSaleStatuses = [ProductStatus.FOR_SALE];
    const pendingStatuses = [ProductStatus.PENDING];
    const salePendingStatuses = [ProductStatus.SALE_PENDING];
    this.lotsService.getProductsByStatusOfCurrentClient(forSaleStatuses).subscribe(lots => {
      this._pureForSaleProducts = lots;
      this.setActiveGroup(this.activeGroup);
    });

    this.lotsService.getProductsByStatusOfCurrentClient(pendingStatuses).subscribe(lots => {
      this._purePendingProducts = lots;
      this.setActiveGroup(this.activeGroup);
    });

    this.lotsService.getProductsByStatusOfCurrentClient(salePendingStatuses).subscribe(lots => {
      this._pureSalePendingProducts = lots;
      this.setActiveGroup(this.activeGroup);
    });

    this.route.queryParams
      .subscribe(params => {
        this.setActiveGroup(ProductGroup[(params['activeTab'] || 'FOR_SALE') as ProductGroup]);
      });

  }

  setActiveGroup(group: ProductGroup) {
    this.activeGroup = group;
    switch (this.activeGroup) {
      case        ProductGroup.SALE_PENDING: {
        this._allItems = this._pureSalePendingProducts;
        break;
      }
      case        ProductGroup.PENDING: {
        this._allItems = this._purePendingProducts;
        break;
      }
      case        ProductGroup.FOR_SALE: {
        this._allItems = this._pureForSaleProducts;
        break;
      }
      default: {
        this.activeGroup = ProductGroup.FOR_SALE;
        this._allItems = this._pureForSaleProducts;
        break;
      }
    }
    this.search();
  }


}
