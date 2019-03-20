import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LetsTryComponent} from './lets-try.component';

const routes: Routes = [
  {path: '', pathMatch: 'full', component: LetsTryComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LetsTryRoutingModule {
}
