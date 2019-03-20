import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Dictionary} from '../create-product/create-product.interfaces';
import {onlyNumber} from '../create-product/create-product.contstances';
import {ActivatedRoute, Router} from '@angular/router';
import {EditProductService} from './edit-product.service';
import {Product} from '../../shared/models';
import {LotsService} from '../../shared/services/lots.service';
import {validateAllFormFields} from '../../shared/functions';
import {ERRORS} from '../../shared/constants/errors';

@Component({
  selector: 'afe-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.scss']
})
export class EditProductComponent implements OnInit {

  public editForm: FormGroup;

  public loading: boolean;
  public error: string;

  public dictionary: Dictionary[];
  public product: any;

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private lotsService: LotsService,
              private route: ActivatedRoute,
              private editProductService: EditProductService) {
    this.editForm = this.formBuilder.group({
      name: ['', Validators.required],
      startingBid: ['', Validators.compose([Validators.required, onlyNumber])],
      buyOutPrice: ['', Validators.compose([Validators.required, onlyNumber])],
      // documentFiles: ['', Validators.required],
      description: ['', Validators.required],
      platformId: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
    });
    this.loading = false;
    this.error = null;
  }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadProduct(id);
    } else {
      this.router.navigateByUrl('not-found');
    }

    this.editProductService.getDictionary().subscribe(dictionary => {
      this.dictionary = dictionary;
    });
  }

  public get name() {
    return this.editForm.controls['name'];
  }

  public get startingBid() {
    return this.editForm.controls['startingBid'];
  }

  public get buyOutPrice() {
    return this.editForm.controls['buyOutPrice'];
  }

  public get documentFiles() {
    return this.editForm.controls['documentFiles'];
  }

  public get description() {
    return this.editForm.controls['description'];
  }

  public get startDate() {
    return this.editForm.controls['startDate'];
  }

  public get endDate() {
    return this.editForm.controls['endDate'];
  }

  loadProduct(id) {
    this.loading = true;
    this.error = null;
    this.lotsService.getProduct(id).subscribe(i => {
        this.product = i;
        this.loadForm(i);
        this.loading = false;
      },
      e => {
        this.error = ERRORS[e.error.type];
        this.loading = false;
      });
  }

  loadForm(product: Product) {
    this.editForm.reset();
    this.editForm.controls['name'].setValue(product.name);
    this.editForm.controls['startingBid'].setValue(product.startingBid);
    this.editForm.controls['buyOutPrice'].setValue(product.buyOutPrice);
    this.editForm.controls['description'].setValue(product.description);
    this.editForm.controls['platformId'].setValue(product.platformId);
    this.editForm.controls['startDate'].setValue(product.startDate);
    this.editForm.controls['endDate'].setValue(product.endDate);
  }

  submit() {
    if (this.editForm.invalid) {
      validateAllFormFields(this.editForm);
    } else {
      this.loading = true;
      this.editProductService.updateProduct(Object.assign({}, this.product, this.editForm.getRawValue()))
        .subscribe(i => {
          this.router.navigateByUrl('/lot/' + this.product.productId);
          this.loading = false;
        }, e => {
          this.loading = false;
          this.error = ERRORS[e.error.type];
        });
    }
  }

}
