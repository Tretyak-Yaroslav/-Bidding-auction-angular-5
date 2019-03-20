import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AcquiredRoutingModule } from './acquired-routing.module';
import { AcquiredComponent } from './acquired.component';
import {AlertModule} from '../../modules/alert/alert.module';
import {PaginationModule} from 'ngx-bootstrap/pagination';
import {SharedModule} from '../../shared/shared.module';
import {ReactiveFormsModule} from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    PaginationModule,
    ReactiveFormsModule,
    SharedModule,
    AlertModule,
    AcquiredRoutingModule
  ],
  declarations: [AcquiredComponent]
})
export class AcquiredModule { }
