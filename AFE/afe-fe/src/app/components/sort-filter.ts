import {SortDirection} from '../shared/models';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {PageChangedEvent} from 'ngx-bootstrap';

export class SortFilter {
  public _activePage: number;

  public _allItems: any[];
  public availableItems: any[];
  public activeItems: any[];

  public searchForm: FormGroup;
  public itemsPerPage: number;
  public listView: boolean;
  public sortBy: string;
  public searchBy: string;
  public sortDir: SortDirection;

  constructor(formBuilder: FormBuilder) {
    this.searchForm = formBuilder.group({
      search: ['', Validators.required],
    });
    this.sortBy = 'id';
    this.searchBy = 'id';
    this.sortDir = SortDirection.ASC;
    this._activePage = 1;
    this.itemsPerPage = 12;
    this._allItems = null;
    this.availableItems = null;
    this.activeItems = null;
    this.listView = true;
  }

  search() {
    if (this._allItems) {
      this.availableItems = this._allItems.filter(i =>
        i[this.searchBy].toLocaleLowerCase().indexOf(this.searchForm.controls['search'].value.toLocaleLowerCase()) !== -1);
    }
    this.filter();
  }

  filter() {
    if (this.availableItems) {
      const startItem = (this._activePage - 1) * this.itemsPerPage;
      const endItem = this._activePage * this.itemsPerPage;
      this.activeItems = this.availableItems.slice(startItem, endItem);
    }
  }

  sortRefresh() {
    this.sort(this.sortBy, this.sortDir);
  }

  sortAuto(variable: string) {
    this.sortBy = variable;
    this.sort(this.sortBy, this.sortDir);
  }

  sortWithDirSwitch(variable: string) {
    if (this.sortBy === variable) {
      this.sortDir = this.sortDir === SortDirection.ASC ? SortDirection.DESC : SortDirection.ASC;
    }
    this.sortBy = variable;
    this.sort(this.sortBy, this.sortDir);
  }

  sort(variable: string, dir: SortDirection) {
    this.sortBy = variable;
    this.sortDir = dir;

    if (this._allItems) {
      this._allItems = this._allItems.sort((a, b) => {
        if (a[this.sortBy] == null || b[this.sortBy] == null) {
          return 0;
        }
        let res = 0;

        if (typeof a[this.sortBy] === 'string') {
          res = (a[this.sortBy][0] > b[this.sortBy][0] ? 1 : -1);
        } else {
          res = a[this.sortBy] - b[this.sortBy];
        }

        return (dir === SortDirection.ASC ? res : res * -1);
      });
      this.search();
    }
    // this.changeDetectorRef.detectChanges();
    // this.changeDetectorRef.markForCheck();
  }

  pageChanged(event: PageChangedEvent): void {
    this._activePage = event.page;
    this.search();
  }
}
