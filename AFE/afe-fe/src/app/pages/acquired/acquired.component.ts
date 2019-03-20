import {Component, OnInit} from '@angular/core';
import {UserService} from '../../shared/services/user.service';
import {Product, ProductStatus, SortDirection} from '../../shared/models';
import {LotsService} from '../../shared/services/lots.service';
import {FormBuilder} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AlertService} from '../../modules/alert/alert.service';
import {SortFilter} from '../../components/sort-filter';
import {PRODUCTS_PHOTO_GET_URL} from '../../shared/shared.constants';
import {ERRORS} from '../../shared/constants/errors';

export enum ProductGroup {
  OWNED,
  EXPIRED
}

@Component({
  selector: 'afe-acquired',
  templateUrl: './acquired.component.html',
  styleUrls: ['./acquired.component.scss']
})
export class AcquiredComponent extends SortFilter implements OnInit {
  ProductGroup = ProductGroup;
  ProductStatus = ProductStatus;
  PRODUCTS_PHOTO_GET_URL = PRODUCTS_PHOTO_GET_URL;

  public activeGroup: ProductGroup;
  public _pureOwnedProducts: Product[];
  public _pureExpiredProducts: Product[];

  public ownedError: string;
  public expiredError: string;

  activeItems: Product[];

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private alertService: AlertService,
              private route: ActivatedRoute,
              private userService: UserService,
              private lotsService: LotsService) {
    super(formBuilder);
    this.searchBy = 'name';
    this.ownedError = null;
    this.expiredError = null;
    this.activeGroup = ProductGroup.OWNED;
  }

  ngOnInit() {
    const ownedStatuses = [ProductStatus.OWNED];
    const expiredStatuses = [ProductStatus.EXPIRED, ProductStatus.REJECTED];

    this.lotsService.getProductsByStatusOfCurrentClient(ownedStatuses)
      .subscribe(lots => {
        this._pureOwnedProducts = lots;
        this.setActiveGroup(this.activeGroup);
      }, e => this.ownedError = ERRORS[e.type]);

    this.lotsService.getProductsByStatusOfCurrentClient(expiredStatuses)
      .subscribe(lots => {
        this._pureExpiredProducts = lots;
        this.setActiveGroup(this.activeGroup);
      }, e => this.expiredError = ERRORS[e.type]);
  }

  setActiveGroup(group: ProductGroup) {
    this.activeGroup = group;
    switch (this.activeGroup) {
      case        ProductGroup.OWNED: {
        this._allItems = this._pureOwnedProducts;
        break;
      }
      case        ProductGroup.EXPIRED: {
        this._allItems = this._pureExpiredProducts;
        break;
      }
    }
    this.search();
  }


}
