import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {validateAllFormFields} from '../../shared/functions';

@Component({
  selector: 'afe-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.scss']
})
export class ContactsComponent implements OnInit {

  public contactForm: FormGroup;

  public loading: boolean;
  public error: boolean;


  constructor(private formBuilder: FormBuilder,
              private router: Router) {
    this.contactForm = this.formBuilder.group({
      email: ['', Validators.required],
      name: ['', Validators.required],
      message: ['', Validators.required],
    });
    this.loading = false;
    this.error = false;
  }

  ngOnInit() {
  }

  public get email() {
    return this.contactForm.controls['email'];
  }

  public get name() {
    return this.contactForm.controls['name'];
  }

  public get message() {
    return this.contactForm.controls['message'];
  }

  submit() {
    if (this.contactForm.invalid) {
      validateAllFormFields(this.contactForm);
    } else {
      this.loading = false;
      this.error = false;
    }

  }
}
