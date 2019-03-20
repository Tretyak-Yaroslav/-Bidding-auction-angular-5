import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {DictionaryRoutingModule} from './dictionary-routing.module';
import {DictionaryComponent} from './dictionary.component';
import {DictionaryService} from './dictionary.service';
import {HttpClientModule} from '@angular/common/http';

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    DictionaryRoutingModule
  ],
  declarations: [DictionaryComponent],
  providers: [DictionaryService]
})
export class DictionaryModule {
}
