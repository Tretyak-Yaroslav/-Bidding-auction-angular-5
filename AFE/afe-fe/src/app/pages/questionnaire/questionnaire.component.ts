import {Component, OnInit} from '@angular/core';
import {FormGroup, Validators} from '@angular/forms';
import {QuestionnaireService} from './questionnaire.service';
import {arrayToFormGroup, validateAllFormFields} from '../../shared/functions';
import {Question, Questionnaire, QuestionnaireUpdate} from './questionnaire.interfaces';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'afe-questionnaire',
  templateUrl: './questionnaire.component.html',
  styleUrls: ['./questionnaire.component.scss']
})
export class QuestionnaireComponent implements OnInit {

  private _questionnaire: Questionnaire;
  public questions: Question[];
  public questionnaireForm: FormGroup;

  public returnUrl = '/publications';

  public loading: boolean;

  constructor(private questionnaireService: QuestionnaireService,
              private router: Router,
              private route: ActivatedRoute) {
    this.questions = [];
    this.questionnaireForm = arrayToFormGroup(this.questions, 'code', [Validators.required]);
    this.loading = false;
  }

  ngOnInit() {
    this.loading = true;
    this.questionnaireService.getQuestionnaire().subscribe(questionnaire => {
      this._questionnaire = questionnaire;
      this.questions = questionnaire.questions;
      this.loading = false;
      this.questionnaireForm = arrayToFormGroup(this.questions, 'code', [Validators.required]);
    });
    this.route.queryParams
      .subscribe(params => this.returnUrl = params['returnUrl'] || '/publications');
  }

  public reset() {
    this.questionnaireForm.reset();
  }

  public skip() {
    this.router.navigateByUrl(this.returnUrl);
  }

  public submit() {
    if (this.questionnaireForm.invalid) {
      validateAllFormFields(this.questionnaireForm);
    } else {
      this.loading = true;
      const questionnaire: QuestionnaireUpdate = new QuestionnaireUpdate();
      questionnaire.id = this._questionnaire.id;
      questionnaire.answers = this.questionnaireForm.getRawValue();

      this.questionnaireService.updateQuestionnaire(questionnaire).subscribe(i => {
          this.router.navigateByUrl(this.returnUrl);
          this.loading = false;
        },
        e => this.router.navigateByUrl(this.returnUrl));
    }

  }

}
