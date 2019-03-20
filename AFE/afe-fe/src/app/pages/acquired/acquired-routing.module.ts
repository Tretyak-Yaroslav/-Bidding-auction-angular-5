import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AcquiredComponent} from './acquired.component';

const routes: Routes = [
  {path: '', pathMatch: 'full', component: AcquiredComponent}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AcquiredRoutingModule { }
