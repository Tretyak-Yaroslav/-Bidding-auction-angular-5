import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {CreateProductService} from './create-product.service';
import {Dictionary} from './create-product.interfaces';
import {Router} from '@angular/router';
import {onlyNumber} from './create-product.contstances';
import {validateAllFormFields} from '../../shared/functions';
import {ERRORS} from '../../shared/constants/errors';

@Component({
  selector: 'afe-create-product',
  templateUrl: './create-product.component.html',
  styleUrls: ['./create-product.component.scss']
})
export class CreateProductComponent implements OnInit {

  public createForm: FormGroup;

  public loading: boolean;
  public error: string;

  public blob: any;

  public dictionary: Dictionary[];

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private createProductService: CreateProductService) {
    this.createForm = this.formBuilder.group({
      name: ['', Validators.required],
      startingBid: ['', Validators.compose([Validators.required, onlyNumber])],
      buyOutPrice: ['', Validators.compose([Validators.required, onlyNumber])],
      photo: ['', Validators.required],
      description: ['', Validators.required],
      platformId: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
    });
    this.loading = false;
    this.error = null;
  }

  ngOnInit() {
    this.createProductService.getDictionary().subscribe(dictionary => {
      this.dictionary = dictionary;
    });
  }

  public get name() {
    return this.createForm.controls['name'];
  }

  public get startingBid() {
    return this.createForm.controls['startingBid'];
  }

  public get buyOutPrice() {
    return this.createForm.controls['buyOutPrice'];
  }

  public get photo() {
    return this.createForm.controls['photo'];
  }

  public get description() {
    return this.createForm.controls['description'];
  }

  public get startDate() {
    return this.createForm.controls['startDate'];
  }

  public get endDate() {
    return this.createForm.controls['endDate'];
  }

  setPhoto(e) {
    this.blob = e.target.files[0];
    this.photo.patchValue(this.blob.name);
  }

  submit() {
    if (this.createForm.invalid) {
      validateAllFormFields(this.createForm);
    } else {
      this.loading = true;
      this.error = null;
      this.createProductService.createProduct(this.createForm.getRawValue()).subscribe(i => {
        const body = new FormData();
        body.append('file', this.blob);
        this.createProductService.updatePhoto(body, i.productId).subscribe((j) => {
          this.router.navigate(['/publications'], {queryParams: {activeTab: 'PENDING'}});
          this.loading = false;
        }, er => {
          this.error = er.detail; // ERRORS[er.type];
          this.loading = false;
        });
      }, e => {
        this.error =  e.detail; // ERRORS[e.type];
        this.loading = false;
      });
    }

  }

}
