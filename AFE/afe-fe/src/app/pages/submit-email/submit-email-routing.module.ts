import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {SubmitEmailComponent} from './submit-email.component';

const routes: Routes = [
  {path: '', pathMatch: 'full', component: SubmitEmailComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SubmitEmailRoutingModule { }
