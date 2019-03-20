import {Component, OnInit} from '@angular/core';
import {UserService} from '../../shared/services/user.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {LotsService} from '../../shared/services/lots.service';
import {Product, ProductSortType} from '../../shared/models';
import {AuthenticationService} from '../../shared/services/authentication.service';

@Component({
  selector: 'afe-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.scss']
})
export class LandingComponent implements OnInit {

  public searchForm: FormGroup;
  public latestProduct: Product[] = null;
  public hottestProduct: Product[] = null;

  constructor(private userService: UserService,
              private router: Router,
              private auth: AuthenticationService,
              private lotsService: LotsService,
              private formBuilder: FormBuilder) {
    this.searchForm = this.formBuilder.group({
      search: ['', Validators.required],
    });
  }

  ngOnInit() {
    this.lotsService.getRange(12, ProductSortType.CREATE_DATE).subscribe((lots) => {
      this.latestProduct = lots;
    });
    this.lotsService.getRange(12, ProductSortType.BID_COUNT).subscribe((lots) => {
      this.hottestProduct = lots;
    });
  }

  search() {
    this.router.navigate(['/search'], {
      queryParams: {
        text: this.searchForm.controls['search'].value
      }
    });
    this.searchForm.controls['search'].setValue('');
  }

  makeBid(id) {
    this.auth.isAuthorized().subscribe(i => {
      if (i) {
        this.router.navigate(['/lot/' + id]);
      } else {
        this.router.navigate(['login'], {queryParams: {returnUrl: '/lot/' + id}});
      }
    });
  }

}
