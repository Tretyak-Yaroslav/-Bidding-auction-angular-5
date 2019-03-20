import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {EditProductRoutingModule} from './edit-product-routing.module';
import {EditProductComponent} from './edit-product.component';
import {OwlDateTimeModule, OwlNativeDateTimeModule} from 'ng-pick-datetime';
import {HttpClientModule} from '@angular/common/http';
import {ReactiveFormsModule} from '@angular/forms';
import {EditProductService} from './edit-product.service';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HttpClientModule,
    OwlDateTimeModule,
    OwlNativeDateTimeModule,
    EditProductRoutingModule
  ],
  providers: [EditProductService],
  declarations: [EditProductComponent]
})
export class EditProductModule {
}
