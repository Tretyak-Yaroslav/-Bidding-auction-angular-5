import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ViewProductComponent} from './view-product.component';

const routes: Routes = [
  {path: ':id', pathMatch: 'full', component: ViewProductComponent}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ViewProductRoutingModule { }