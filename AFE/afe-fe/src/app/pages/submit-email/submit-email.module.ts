import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SubmitEmailRoutingModule } from './submit-email-routing.module';
import { SubmitEmailComponent } from './submit-email.component';
import {HttpClientModule} from '@angular/common/http';

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    SubmitEmailRoutingModule
  ],
  declarations: [SubmitEmailComponent]
})
export class SubmitEmailModule { }
