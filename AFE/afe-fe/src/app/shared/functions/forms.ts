import {FormControl, FormGroup, Validators} from '@angular/forms';

export function arrayToFormGroup(questions: any[], indexBy?: string, validators?: any[]) {
  const key = indexBy ? indexBy : 'id';
  const group: any = {};

  questions.forEach(question => {
    group[question[key]] = new FormControl('', Validators.compose(validators));
  });

  return new FormGroup(group);
}


export function validateAllFormFields(formGroup: FormGroup) {
  Object.keys(formGroup.controls).forEach(field => {
    const control = formGroup.get(field);
    if (control instanceof FormControl) {
      control.markAsDirty({onlySelf: true});
    } else if (control instanceof FormGroup) {
      validateAllFormFields(control);
    }
  });
}
