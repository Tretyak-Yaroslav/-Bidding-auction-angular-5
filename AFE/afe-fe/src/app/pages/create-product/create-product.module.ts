import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {CreateProductRoutingModule} from './create-product-routing.module';
import {CreateProductComponent} from './create-product.component';
import {ReactiveFormsModule} from '@angular/forms';
import {OwlDateTimeModule, OwlNativeDateTimeModule} from 'ng-pick-datetime';
import {CreateProductService} from './create-product.service';
import {HttpClientModule} from '@angular/common/http';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    CreateProductRoutingModule,
    OwlDateTimeModule,
    OwlNativeDateTimeModule,
  ],
  providers: [CreateProductService],
  declarations: [CreateProductComponent]
})
export class CreateProductModule {
}
