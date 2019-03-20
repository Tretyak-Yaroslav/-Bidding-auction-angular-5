import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {QuestionnaireRoutingModule} from './questionnaire-routing.module';
import {QuestionnaireComponent} from './questionnaire.component';
import {ReactiveFormsModule} from '@angular/forms';
import {QuestionnaireService} from './questionnaire.service';
import {HttpClientModule} from '@angular/common/http';

@NgModule({
  imports: [
    CommonModule,
    QuestionnaireRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  declarations: [QuestionnaireComponent],
  providers: [QuestionnaireService]
})
export class QuestionnaireModule {
}
