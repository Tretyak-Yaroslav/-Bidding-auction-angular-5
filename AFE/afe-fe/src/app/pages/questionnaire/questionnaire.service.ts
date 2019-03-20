import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Questionnaire, QuestionnaireUpdate} from './questionnaire.interfaces';
import {HttpClient} from '@angular/common/http';
import {QUESTIONNAIRE_CURRENT_GET_URL, QUESTIONNAIRE_UPDATE_POST_URL} from './questionnaire.constants';

@Injectable()
export class QuestionnaireService {

  constructor(private http: HttpClient) {
  }

  public getQuestionnaire(): Observable<Questionnaire> {
    return this.http.get<{ questionnaire: Questionnaire }>(QUESTIONNAIRE_CURRENT_GET_URL)
      .map(i => i.questionnaire);
  }

  public updateQuestionnaire(questionnaire: QuestionnaireUpdate): Observable<any> {
    return this.http.post<any>(QUESTIONNAIRE_UPDATE_POST_URL, questionnaire);
  }

}
