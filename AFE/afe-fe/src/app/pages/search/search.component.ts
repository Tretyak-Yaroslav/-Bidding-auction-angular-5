import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {Product, ProductStatus, SortDirection} from '../../shared/models';
import {LotsService} from '../../shared/services/lots.service';
import {SortFilter} from '../../components/sort-filter';
import {SEARCH_PARAMS} from './search.constants';
import {ServerTime} from '../../shared/services/authentication.service';
import {PRODUCTS_PHOTO_GET_URL} from '../../shared/shared.constants';


@Component({
  selector: 'afe-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent extends SortFilter implements OnInit, OnDestroy {

  SortDirection = SortDirection;
  PRODUCTS_PHOTO_GET_URL = PRODUCTS_PHOTO_GET_URL;
  activeItems: Product[];
  currentTime: Date;

  public change: boolean;
  public timeUpdate;

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private route: ActivatedRoute,
              private lotsService: LotsService) {
    super(formBuilder);
    this.searchBy = 'name';
    this.sortBy = 'name';
    this.currentTime = ServerTime.getCurrent();
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.searchForm.controls['search'].setValue(params[SEARCH_PARAMS.text] || '');
      this.sortBy = params[SEARCH_PARAMS.sort] || this.sortBy;
      this.sort(this.sortBy, SortDirection.ASC);
    });

    this.lotsService.getAllProducts().subscribe(lots => {
      this._allItems = lots.filter(i => i.productStatus == ProductStatus.FOR_SALE);
      this.sort(this.sortBy, SortDirection.ASC);
    });

    this.timeUpdate = setInterval(() => this.change = !this.change, 1000);
  }

  ngOnDestroy() {
    clearInterval(this.timeUpdate);
  }

}
