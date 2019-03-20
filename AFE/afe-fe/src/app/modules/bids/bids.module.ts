import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {BidsComponent} from './bids.component';
import {HttpClientModule} from '@angular/common/http';
import {BidsService} from './bids.service';

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
  ],
  providers: [BidsService],
  declarations: [BidsComponent],
  exports: [BidsComponent]
})
export class BidsModule {
}
