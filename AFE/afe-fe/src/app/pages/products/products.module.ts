import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ProductsRoutingModule} from './products-routing.module';
import {ProductsComponent} from './products.component';
import {PaginationModule} from 'ngx-bootstrap/pagination';
import {SharedModule} from '../../shared/shared.module';
import {ReactiveFormsModule} from '@angular/forms';
import {ProductsService} from './products.service';
import {HttpClientModule} from '@angular/common/http';
import {BidsModule} from '../../modules/bids/bids.module';

@NgModule({
  imports: [
    CommonModule,
    PaginationModule,
    HttpClientModule,
    ReactiveFormsModule,
    BidsModule,
    SharedModule,
    ProductsRoutingModule
  ],
  declarations: [ProductsComponent],
  providers: [ProductsService]
})
export class ProductsModule {
}
