import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from './shared/services/authentication.service';

@Component({
  selector: 'afe-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  constructor(private  authenticationService: AuthenticationService) {

  }

  onDeactivate() {
    window.scrollTo(0, 0);
  }

  ngOnInit(): void {
    this.authenticationService.loadTime();
  }
}
