import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Permission, UserService} from '../../shared/services/user.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Client} from '../../shared/models';
import {confirmSamePasswords} from '../registration/registration.component';
import {ERRORS as _ERRORS} from '../../shared/constants/errors';
import {USER_AVATAR_GET_URL} from '../../shared/shared.constants';
import {validateAllFormFields} from '../../shared/functions';

const ERRORS = _ERRORS;

@Component({
  selector: 'afe-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit {
  Permission = Permission;
  USER_AVATAR_GET_URL = USER_AVATAR_GET_URL;

  public passwordForm: FormGroup;
  public userInfoForm: FormGroup;

  public errorPasswordMessage: string | null;
  public errorUserMessage: string | null;

  public manager: boolean;
  public client: Client;
  clientAvatar = ''; // ./assets/images/default_avatar.png

  constructor(private userService: UserService, private formBuilder: FormBuilder, private changeDetectorRef: ChangeDetectorRef) {
    this.client = new Client();
    this.passwordForm = this.formBuilder.group({
      passwordOld: ['', Validators.compose([Validators.required, Validators.minLength(1)])],
      password: ['', Validators.compose([Validators.required, Validators.minLength(1)])],
      confirmPassword: ['', Validators.compose([Validators.required, Validators.minLength(1)])],
    }, {
      validator: [confirmSamePasswords.bind(this.passwordForm)]
    });

    this.userInfoForm = this.formBuilder.group({
      name: ['', Validators.compose([Validators.required, Validators.minLength(1)])],
      birthday: ['', Validators.compose([Validators.required, Validators.minLength(1)])],
      phoneNumber: ['', Validators.compose([Validators.required, Validators.minLength(1)])],
      // email: ['', Validators.compose([Validators.required, Validators.minLength(1)])],
    });

    this.manager = null;
    this.errorPasswordMessage = null;
    this.errorUserMessage = null;
  }

  ngOnInit() {
    this.userService.getCurrentClient().subscribe(client => {
      this.client = client;
      this.clientAvatar = client.avatarSrc;
      this.userInfoForm.controls['name'].setValue(client.name);
      if (client.birthday) {
        this.userInfoForm.controls['birthday'].setValue(client.birthday);
      }
      this.userInfoForm.controls['phoneNumber'].setValue(client.phoneNumber);
      this.userInfoForm.markAsUntouched();
      //   this.userInfoForm.controls['email'].setValue(client.email);
    });

    this.userService.getPermissions().subscribe(perms => {
      if (this.userService.hasPermission(Permission._IS_BACK_OFFICE, perms)) {
        this.manager = true;
      } else {
        this.manager = false;
      }
    });
  }

  public get name() {
    return this.userInfoForm.controls['name'];
  }

  public get phone() {
    return this.userInfoForm.controls['phoneNumber'];
  }

  public get birth() {
    return this.userInfoForm.controls['birthday'];
  }

  public get email() {
    return this.userInfoForm.controls['birthday'];
    //  return this.userInfoForm.controls['email'];
  }

  public get passwordOld() {
    return this.passwordForm.controls['passwordOld'];
  }

  public get password() {
    return this.passwordForm.controls['password'];
  }

  public get confirmPassword() {
    return this.passwordForm.controls['confirmPassword'];
  }

  resetPassword() {
    this.passwordForm.reset();
  }

  updatePassword() {
    if (this.passwordForm.invalid) {
      validateAllFormFields(this.passwordForm);
    } else {
      const form = this.passwordForm.getRawValue();
      const req = {
        currentPassword: form.passwordOld,
        newPassword: form.password
      };
      this.errorPasswordMessage = null;
      this.userService.updatePassword(req).subscribe(i => {
        this.passwordForm.markAsPristine();
        this.passwordForm.reset();
      }, e => {
        this.errorPasswordMessage = ERRORS[e.type];
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

  updateUserInfo() {
    if (this.userInfoForm.invalid) {
      validateAllFormFields(this.userInfoForm);
    } else {
      this.errorUserMessage = null;
      this.userService.updateInfo(this.userInfoForm.getRawValue()).subscribe(i => {
        this.userInfoForm.markAsPristine();
        // TODO: success
      }, e => {
        this.errorUserMessage = ERRORS[e.type];
      });
    }
  }


  updateAvatar(e) {
    const blob = e.target.files[0];
    const body = new FormData();
    body.append('file', blob);
    this.userService.updateAvatar(body)
      .subscribe(i => {
        this.clientAvatar = USER_AVATAR_GET_URL + this.client.clientId + '?rand=' + Math.random();
        this.changeDetectorRef.detectChanges();
      });
  }

  resetUserInfo() {
    this.userInfoForm.reset();
    this.userInfoForm.controls['name'].setValue(this.client.name);
    if (this.client.birthday) {
      this.userInfoForm.controls['birthday'].setValue(this.client.birthday);
    }
    this.userInfoForm.controls['phoneNumber'].setValue(this.client.phoneNumber);
  }

}
