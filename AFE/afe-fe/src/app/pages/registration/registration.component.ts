import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthenticationService} from '../../shared/services/authentication.service';
import {ERRORS as _ERRORS} from '../../shared/constants/errors';
import {AlertService} from '../../modules/alert/alert.service';
import {AlertButtonType, IAlertButton, IAlertIcon, IAlertRequest, IconType} from '../../modules/alert/alert.interfaces';

export function confirmSamePasswords(group: FormGroup) {
  const password = group.controls['password'].value;
  const confirmPassword = group.controls['confirmPassword'].value;
  return password === confirmPassword ? null : {passwordsDifferent: true};
}

function validateAllFormFields(formGroup: FormGroup) {
  Object.keys(formGroup.controls).forEach(field => {
    const control = formGroup.get(field);
    if (control instanceof FormControl) {
      control.markAsDirty({onlySelf: true});
      control.markAsTouched({onlySelf: true});
    } else if (control instanceof FormGroup) {
      validateAllFormFields(control);
    }
  });
}

const ERRORS = _ERRORS;

ERRORS['ERR002'] = 'Email is formatted incorrectly';

@Component({
  selector: 'afe-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {

  public registrationForm: FormGroup;

  public passwordIcon: string;
  public passwordConfirmIcon: string;

  public loading: boolean;
  public errorMessage: string | null;
  public returnUrl: string;

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private alertService: AlertService,
              private route: ActivatedRoute,
              private  authService: AuthenticationService) {
    this.registrationForm = this.formBuilder.group({
      fullName: ['', Validators.compose([Validators.required, Validators.minLength(1)])],
      email: ['', Validators.compose([Validators.required, Validators.minLength(1), Validators.email])],
      password: ['', Validators.compose([Validators.required, Validators.minLength(1)])],
      confirmPassword: ['', Validators.compose([Validators.required, Validators.minLength(1)])],
      consent: [false, Validators.requiredTrue]
    }, {
      validator: [confirmSamePasswords.bind(this.registrationForm)]
    });
    this.passwordIcon = 'visibility';
    this.passwordConfirmIcon = 'visibility';
    this.returnUrl = '/publications';
    this.loading = false;
    this.errorMessage = null;

    this.registrationForm.controls['consent'].markAsDirty();
  }

  ngOnInit() {
    this.route.queryParams
      .subscribe(params => this.returnUrl = params['returnUrl'] || '/publications');
  }

  public get fullName() {
    return this.registrationForm.controls['fullName'];
  }

  public get email() {
    return this.registrationForm.controls['email'];
  }

  public get password() {
    return this.registrationForm.controls['password'];
  }

  public get confirmPassword() {
    return this.registrationForm.controls['confirmPassword'];
  }

  public get consent() {
    return this.registrationForm.controls['consent'];
  }

  submit() {
    if (this.registrationForm.invalid) {
      validateAllFormFields(this.registrationForm);
    } else {
      this.loading = true;
      this.errorMessage = null;
      this.authService.registration(this.registrationForm.value).subscribe((res) => {
        this.loading = false;
        const req: IAlertRequest = {
          caption: 'Congratulation',
          iconType: Object.assign(new IAlertIcon(), {src: './assets/images/logo.png', classes: 'height-130'}),
          alertButtons: [Object.assign(new IAlertButton(), {
            isConfirm: true,
            classes: 'btn btn-block btn-primary py-2',
            title: 'OK'
          })],
          text: 'For enter to your AFE account. Please open your email and verificate it.'
        };
        this.alertService.create(req).subscribe(() => {
          this.router.navigate(['/login'], {queryParams: {returnUrl: this.returnUrl}});
        });
      }, e => {
        this.loading = false;
        this.errorMessage = ERRORS[e.type];
      });
    }
  }

  public toggleVisibility(elementRef: any) {
    if (elementRef.type === 'password') {
      elementRef.type = 'text';
    } else {
      elementRef.type = 'password';
    }
  }

}
