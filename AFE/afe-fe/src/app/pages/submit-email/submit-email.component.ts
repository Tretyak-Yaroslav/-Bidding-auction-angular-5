import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {ERRORS} from '../../shared/constants/errors';

@Component({
  selector: 'afe-submit-email',
  templateUrl: './submit-email.component.html',
  styleUrls: ['./submit-email.component.scss']
})
export class SubmitEmailComponent implements OnInit {

  public token: string;
  public errorMessage: string;
  public successMessage: string;
  public loader: boolean;

  constructor(
    private http: HttpClient,
    private route: ActivatedRoute,
    private router: Router,
  ) {
  }

  ngOnInit() {
    this.route.queryParams
      .subscribe(params => {
        this.token = params['token'] || null;
        if (this.token) {
          this._submitToken(this.token);
        } else {
          this.router.navigateByUrl('not-found');
        }
      });
  }

  private _submitToken(token: string) {
    this.errorMessage = undefined;
    this.successMessage = undefined;
    this.loader = true;
    this.http.get('/api/v1/auth/confirm/email/' + token).subscribe(
      i => {
        this.successMessage = 'Your email are verified!';
        this.loader = false;

        setTimeout(() => {
          this.router.navigateByUrl('/login');
        }, 5000);
      },
      err => {
        this.loader = false;
        this.errorMessage = ERRORS[err.error.type];
      }
    );

  }

}
