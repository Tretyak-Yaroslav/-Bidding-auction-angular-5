import {ModuleWithProviders, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UserService} from './services/user.service';
import {AuthenticationService} from './services/authentication.service';
import {AuthGuard, ManagerGuard} from './guards';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {LotsService} from './services/lots.service';
import { TimeLeftPipe } from './pipes/time-left.pipe';
import { PermissionDirective } from './directives/permission.directive';
import {AUTH_SERVICE, AuthModule, PROTECTED_FALLBACK_PAGE_URI, PUBLIC_FALLBACK_PAGE_URI} from 'ngx-auth';

@NgModule({
  imports: [
    CommonModule,
    AuthModule,
    HttpClientModule
  ],
  declarations: [
    TimeLeftPipe,
    PermissionDirective
  ],
  exports: [
    TimeLeftPipe,
    PermissionDirective
  ]
})
export class SharedModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: SharedModule,
      providers: [
        AuthGuard,
        ManagerGuard,

        UserService,
        LotsService,

        AuthenticationService,
        { provide: PROTECTED_FALLBACK_PAGE_URI, useValue: '/' },
        { provide: PUBLIC_FALLBACK_PAGE_URI, useValue: '/login' },
        { provide: AUTH_SERVICE, useClass: AuthenticationService }
      ]
    };
  }
}
