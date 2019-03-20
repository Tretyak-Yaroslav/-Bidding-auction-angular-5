import {Component, EventEmitter, HostBinding, Input, OnInit, Output} from '@angular/core';
import {ClientsService} from '../../clients.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Question} from '../../../questionnaire/questionnaire.interfaces';
import {Client} from '../../../../shared/models';

@Component({
  selector: 'afe-risk-rating',
  templateUrl: './risk-rating.component.html',
  styleUrls: ['./risk-rating.component.scss']
})
export class RiskRatingComponent implements OnInit {

  @Input() client: Client;
  // tslint:disable-next-line
  @Output() onClose: EventEmitter<boolean>;
  @HostBinding('class.disabled') public disabled: boolean;
  public appear: boolean;
  public loading: boolean;
  public disappear: boolean;
  public riskForm: FormGroup;
  public questions: Question[];

  constructor(private clientsService: ClientsService,
              private formBuilder: FormBuilder) {
    this.client = new Client();
    this.onClose = new EventEmitter();
    this.riskForm = formBuilder.group({
      risk: ['', Validators.required],
    });
    this.loading = false;
    this.appear = true;
    this.disappear = false;
    this.disabled = false;
    this.questions = null;
  }

  ngOnInit() {
    this.riskForm.controls['risk'].setValue(this.client.atr);
    if (this.client.questionnaire) {
      this.loading = true;
      this.clientsService.getQuestionnaire(this.client.questionnaire.id).subscribe(quest => {
        this.questions = [];
        quest.questions.forEach((question, i) => {
          this.questions.push({
            code: question.code,
            text: question.text,
            answers: [
              question.answers[this.client.questionnaire.questions[i].answers[0].code - 1]
            ]
          });
        });
        this.loading = false;
      });
    }
  }

  show() {
    this.appear = true;
    this.disappear = false;
    this.disabled = false;
  }

  cancel(confirm = false) {
    this.appear = false;
    this.disappear = true;
    this.disabled = true;
    this.onClose.emit(confirm);
  }

  saveRisk() {
    this.client.atr = this.riskForm.getRawValue().risk;
    this.clientsService.setClientRisk(this.client.clientId, this.client.atr).subscribe();
    this.cancel(true);
  }

}
