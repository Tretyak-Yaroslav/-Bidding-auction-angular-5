import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {ContactsComponent} from './contacts.component';

const routes: Routes = [
  {path: '', pathMatch: 'full', component: ContactsComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ContactsRoutingModule {
}
