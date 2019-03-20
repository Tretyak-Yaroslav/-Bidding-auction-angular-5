import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ClientsRoutingModule} from './clients-routing.module';
import {ClientsComponent} from './clients.component';
import {ReactiveFormsModule} from '@angular/forms';
import {PaginationModule} from 'ngx-bootstrap/pagination';
import {SharedModule} from '../../shared/shared.module';
import {ClientsService} from './clients.service';
import {HttpClientModule} from '@angular/common/http';
import {AlertModule} from '../../modules/alert/alert.module';
import {RiskRatingComponent} from './modules/risk-rating/risk-rating.component';

@NgModule({
  imports: [
    CommonModule,
    ClientsRoutingModule,
    PaginationModule,
    HttpClientModule,
    ReactiveFormsModule,
    AlertModule,
    SharedModule
  ],
  declarations: [ClientsComponent, RiskRatingComponent],
  providers: [ClientsService]
})
export class ClientsModule {
}
