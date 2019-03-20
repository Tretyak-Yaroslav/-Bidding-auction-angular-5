import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

export const DICTIONARY_DOWNLOAD_GET_URL = '/api/v1/dictionary/platform/download';
export const DICTIONARY_UPDATE_PUT_URL = '/api/v1/dictionary/platform/upload';

@Injectable()
export class DictionaryService {

  constructor(public http: HttpClient) {
  }

  public download() {
    return this.http.get(DICTIONARY_DOWNLOAD_GET_URL);
  }

  public upload(file) {
    return this.http.put(DICTIONARY_UPDATE_PUT_URL, file);
  }

}
