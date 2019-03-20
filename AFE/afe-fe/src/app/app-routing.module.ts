import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {AuthGuard} from './shared/guards';
import {ManagerGuard} from './shared/guards';
import {LandingComponent} from './pages/landing/landing.component';
import {LoginComponent} from './pages/login/login.component';

const appRoutes: Routes = [
  {path: '', pathMatch: 'full', component: LandingComponent},
  {path: 'search', loadChildren: 'app/pages/search/search.module#SearchModule'},
  {path: 'registration', loadChildren: 'app/pages/registration/registration.module#RegistrationModule'},
  {path: 'login', component: LoginComponent},
  {path: 'not-found', loadChildren: 'app/pages/not-found/not-found.module#NotFoundModule'},
  {path: 'about', loadChildren: 'app/pages/about/about.module#AboutModule'},
  {path: 'contacts', loadChildren: 'app/pages/contacts/contacts.module#ContactsModule'},
  {path: 'privacy-policy', loadChildren: 'app/pages/privacy-policy/privacy-policy.module#PrivacyPolicyModule'},
  {path: 'lets-try', loadChildren: 'app/pages/lets-try/lets-try.module#LetsTryModule'},
  {path: 'help', loadChildren: 'app/pages/help/help.module#HelpModule'},
  {
    path: 'publications',
    canActivate: [AuthGuard],
    loadChildren: 'app/pages/publications/publications.module#PublicationsModule'
  },
  {path: 'acquired', canActivate: [AuthGuard], loadChildren: 'app/pages/acquired/acquired.module#AcquiredModule'},
  {path: 'settings', canActivate: [AuthGuard], loadChildren: 'app/pages/settings/settings.module#SettingsModule'},
  {
    path: 'clients',
    canActivate: [AuthGuard, ManagerGuard],
    loadChildren: 'app/pages/clients/clients.module#ClientsModule'
  },
  {
    path: 'questionnaire',
    canActivate: [AuthGuard],
    loadChildren: 'app/pages/questionnaire/questionnaire.module#QuestionnaireModule'
  },
  {
    path: 'products',
    canActivate: [AuthGuard, ManagerGuard],
    loadChildren: 'app/pages/products/products.module#ProductsModule'
  },
  {
    path: 'dictionary',
    canActivate: [AuthGuard, ManagerGuard],
    loadChildren: 'app/pages/dictionary/dictionary.module#DictionaryModule'
  },
  {
    path: 'create',
    canActivate: [AuthGuard],
    loadChildren: 'app/pages/create-product/create-product.module#CreateProductModule'
  },
  {
    path: 'edit',
    canActivate: [AuthGuard],
    loadChildren: 'app/pages/edit-product/edit-product.module#EditProductModule'
  },
  {
    path: 'lot',
    loadChildren: 'app/pages/view-product/view-product.module#ViewProductModule'
  },
  {
    path: 'submit-email',
    loadChildren: 'app/pages/submit-email/submit-email.module#SubmitEmailModule'
  },

  {path: '**', redirectTo: '/not-found'},
];


@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
