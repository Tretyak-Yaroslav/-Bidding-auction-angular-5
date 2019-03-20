import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LetsTryRoutingModule } from './lets-try-routing.module';
import { LetsTryComponent } from './lets-try.component';

@NgModule({
  imports: [
    CommonModule,
    LetsTryRoutingModule
  ],
  declarations: [LetsTryComponent]
})
export class LetsTryModule { }
