export class Questionnaire {
  id: number;
  questions: Question[];
}

export class Question {
  code: number;
  text?: string;
  answers: Answer[];
}

export class Answer {
  code: number;
  text?: string;
}

export class QuestionnaireUpdate {
  id: number;
  answers: { [id: number]: number }[];
}
