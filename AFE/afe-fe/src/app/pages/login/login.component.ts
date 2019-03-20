import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthenticationService} from '../../shared/services/authentication.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Permission, UserService} from '../../shared/services/user.service';
import {ERRORS as _ERRORS} from '../../shared/constants/errors';
import {validateAllFormFields} from '../../shared/functions';

const ERRORS = _ERRORS;

// ERRORS['ATH003'] = 'Email is formatted incorrectly';

@Component({
  selector: 'afe-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  public loginForm: FormGroup;
  public passwordIcon: string;
  public loading: boolean;
  public errorMessage: string | null;
  public returnUrl = '/publications';

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private route: ActivatedRoute,
              private userService: UserService,
              private  authService: AuthenticationService) {
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', Validators.required],
    });
    this.passwordIcon = 'visibility';
    this.loading = false;
    this.errorMessage = null;
  }

  ngOnInit() {
    this.route.queryParams
      .subscribe(params => this.returnUrl = params['returnUrl'] || '/publications');
  }

  public get password() {
    return this.loginForm.controls['password'];
  }

  public get email() {
    return this.loginForm.controls['email'];
  }

  login() {
    if (this.loginForm.invalid) {
      validateAllFormFields(this.loginForm);
    } else {
      this.loading = true;
      this.errorMessage = null;
      this.authService.login(this.loginForm.value).subscribe((res) => {
        // TODO: improve below, find another solution
        this.userService.getPermissions().subscribe(perms => {
          this.loading = false;
          if (this.userService.hasPermission(Permission._IS_BACK_OFFICE, perms)) {
            this.router.navigateByUrl('/products');
          } else {
            if (res.client.questionnaire === null) {
              this.router.navigate(['/questionnaire'], {queryParams: {returnUrl: this.returnUrl}});
            } else {
              this.router.navigateByUrl(this.returnUrl);
            }
          }
        });
      }, e => {
        this.loading = false;
        this.errorMessage = ERRORS[e.type];
      });
    }
  }

  forwardToRegistration() {
    this.router.navigateByUrl('registration', {queryParams: {returnUrl: this.returnUrl}});
  }

  public toggleVisibility(elementRef: any) {
    if (elementRef.type === 'password') {
      elementRef.type = 'text';
      this.passwordIcon = 'visibility_off';
    } else {
      elementRef.type = 'password';
      this.passwordIcon = 'visibility';
    }
  }


}
