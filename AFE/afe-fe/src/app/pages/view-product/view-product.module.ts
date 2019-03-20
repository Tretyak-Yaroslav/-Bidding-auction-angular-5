import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ViewProductRoutingModule} from './view-product-routing.module';
import {ViewProductComponent} from './view-product.component';
import {SharedModule} from '../../shared/shared.module';
import {HttpClientModule} from '@angular/common/http';
import {ReactiveFormsModule} from '@angular/forms';
import {ViewProductService} from './view-product.service';
import {BidsModule} from '../../modules/bids/bids.module';

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    HttpClientModule,
    ReactiveFormsModule,
    BidsModule,
    ViewProductRoutingModule
  ],
  providers: [ViewProductService],
  declarations: [ViewProductComponent]
})
export class ViewProductModule {
}
