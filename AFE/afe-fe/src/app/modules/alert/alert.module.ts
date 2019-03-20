import { ModuleWithProviders, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AlertComponent } from './alert.component';
import { AlertService } from './alert.service';
import { DEFAULT_CONFIG } from './alert.constants';

@NgModule({
  imports: [CommonModule],
  declarations: [AlertComponent],
  entryComponents: [AlertComponent],
  exports: [AlertComponent]
})
export class AlertModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: AlertModule,
      providers: [
        AlertService,
        {provide: 'alertConfig', useValue: DEFAULT_CONFIG}
      ]
    };
  }
}
