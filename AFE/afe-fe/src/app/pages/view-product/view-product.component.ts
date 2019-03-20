import {Component, OnDestroy, OnInit} from '@angular/core';
import {Client, Product, ProductStatus} from '../../shared/models';
import {LotsService} from '../../shared/services/lots.service';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../shared/services/user.service';
import {ViewProductService} from './view-product.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ServerTime} from '../../shared/services/authentication.service';
import {IAlertButton, IAlertRequest} from '../../modules/alert/alert.interfaces';
import {AlertService} from '../../modules/alert/alert.service';
import {PRODUCTS_PHOTO_GET_URL} from '../../shared/shared.constants';
import {ERRORS} from '../../shared/constants/errors';
import {onlyNumber} from '../create-product/create-product.contstances';

export const BUTTONS = {
  cancel: Object.assign(new IAlertButton(), {classes: 'btn btn-link', isConfirm: false, title: 'Cancel'}),
  approve: Object.assign(new IAlertButton(), {
    classes: 'btn btn-primary',
    isConfirm: true,
    title: 'Buyout'
  })
};


@Component({
  selector: 'afe-view-product',
  templateUrl: './view-product.component.html',
  styleUrls: ['./view-product.component.scss']
})
export class ViewProductComponent implements OnInit, OnDestroy {
  ProductStatus = ProductStatus;
  PRODUCTS_PHOTO_GET_URL = PRODUCTS_PHOTO_GET_URL;

  public product: Product;
  public loading: boolean;
  public error: boolean;
  public owner: boolean;
  public authorized: boolean;

  public client: Client;
  public makeBidForm: FormGroup;
  public currentTime: Date;

  public activeProduct: Product;
  public activeBids: boolean;

  public change: boolean;
  public timeOutUpdate: boolean; // TODO: replace with sse
  public timeUpdate;

  public errorMessage: string;

  constructor(private lotsService: LotsService,
              private router: Router,
              private viewProductService: ViewProductService,
              private formBuilder: FormBuilder,
              private alertService: AlertService,
              private userService: UserService,
              private route: ActivatedRoute) {
    this.loading = false;
    this.error = false;
    this.authorized = null;
    this.errorMessage = null;
    this.owner = false;
    this.timeOutUpdate = true;
    this.currentTime = ServerTime.getCurrent();
    this.makeBidForm = formBuilder.group({
      bid: ['', Validators.compose([Validators.required, onlyNumber])],
    });
    this.change = false;
  }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadProduct(id);
    } else {
      this.router.navigateByUrl('not-found');
    }

    this.userService.getCurrentClient().subscribe(client => {
      this.client = client;
      this.authorized = true;
      if (this.product) {
        this.owner = this.client.clientId === this.product.productId;
      }
    }, e => this.authorized = false);

    this.timeUpdate = setInterval(() => {
      this.change = !this.change;
      if (ServerTime.getCurrent() >= this.product.endDate && this.timeOutUpdate && this.product.productStatus == ProductStatus.FOR_SALE) {
        this.loadProduct(this.product.productId);
        this.timeOutUpdate = false;
      }
    }, 1000);
  }

  ngOnDestroy() {
    clearInterval(this.timeUpdate);
  }

  loadProduct(id: number) {
    this.loading = true;
    this.lotsService.getProduct(id).subscribe(i => {
        this.product = i;
        this.error = false;
        this.loading = false;
        if (this.client) {
          this.owner = this.client.clientId === this.product.productId;
        }
      },
      e => {
        this.error = true;
        this.loading = false;
      });
  }


  makeBit() {
    if (this.isAuthorized()) {
      this.errorMessage = null;
      this.viewProductService.makeBid(this.product.productId, this.makeBidForm.getRawValue().bid)
        .subscribe(i => {
          this.loadProduct(this.product.productId);
        }, err => {
          this.errorMessage = err.error.detail;
        });
    }
  }

  buyOutProduct() {
    if (this.isAuthorized()) {
      const req: IAlertRequest = {
        caption: 'Buyout' + this.product.name,
        text: 'You going to buy a product.',
        alertButtons: [BUTTONS.approve, BUTTONS.cancel]
      };
      this.alertService.create(req).subscribe(res => {
        if (res.confirm) {
          this.errorMessage = null;
          this.viewProductService.buyOutProduct(this.product.productId).subscribe(i => {
            this.loadProduct(this.product.productId);
          }, err => {
            this.errorMessage = err.error.detail;
          });
        }
      });
    }
  }

  isAuthorized(): boolean {
    if (this.authorized === false) {
      this.router.navigate(['/registration'], {queryParams: {returnUrl: this.router.url}});
      return false;
    }
    return true;
  }

  showBids(product: Product) {
    if (this.isAuthorized()) {
      this.activeBids = true;
      this.activeProduct = product;
    }
  }

  onBidsHide(confirm) {
    this.activeBids = false;
  }

  checkUpdate() {

  }
}
