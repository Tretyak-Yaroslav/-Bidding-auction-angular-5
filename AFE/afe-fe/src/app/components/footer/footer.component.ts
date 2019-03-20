import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'afe-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

  public subscribeForm: FormGroup;

  constructor(private formBuilder: FormBuilder) {
    this.subscribeForm = this.formBuilder.group({
      email: ['', Validators.required],
    });
  }

  ngOnInit() {
  }

}
