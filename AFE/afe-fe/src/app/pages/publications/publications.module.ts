import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {PublicationsRoutingModule} from './publications-routing.module';
import {PublicationsComponent} from './publications.component';
import {PaginationModule} from 'ngx-bootstrap/pagination';
import {ReactiveFormsModule} from '@angular/forms';
import {SharedModule} from '../../shared/shared.module';
import {AlertModule} from '../../modules/alert/alert.module';

@NgModule({
  imports: [
    CommonModule,
    PaginationModule,
    ReactiveFormsModule,
    SharedModule,
    AlertModule,
    PublicationsRoutingModule
  ],
  declarations: [
    PublicationsComponent
  ]
})
export class PublicationsModule {
}
