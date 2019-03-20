import {Component, OnInit} from '@angular/core';
import {SortFilter} from '../../components/sort-filter';
import {FormBuilder} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {DICTIONARY_DOWNLOAD_GET_URL, DictionaryService} from './dictionary.service';
import {ERRORS as _ERRORS} from '../../shared/constants/errors';

const ERRORS = _ERRORS;

@Component({
  selector: 'afe-dictionary',
  templateUrl: './dictionary.component.html',
  styleUrls: ['./dictionary.component.scss']
})
export class DictionaryComponent extends SortFilter implements OnInit {
  DICTIONARY_DOWNLOAD_GET_URL = DICTIONARY_DOWNLOAD_GET_URL;
  public loading: boolean;
  public errorMessage: string;

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private dictionaryService: DictionaryService,
              private route: ActivatedRoute) {
    super(formBuilder);
    this.loading = null;
    this.errorMessage = null;
  }

  ngOnInit() {
  }

  upload(event) {
    this.loading = true;
    this.errorMessage = null;
    const blob = event.target.files[0];
    const body = new FormData();
    body.append('file', blob);
    this.dictionaryService.upload(body).subscribe(i => {
      this.loading = false;
    }, e => {
      this.loading = false;
      this.errorMessage = ERRORS[e.type];
    });
  }

  download() {
    this.loading = true;
    this.errorMessage = null;
    this.dictionaryService.download().subscribe(i => {
      this.loading = false;
    }, e => {
      this.loading = false;
      this.errorMessage = ERRORS[e.type];
    });
  }

}
