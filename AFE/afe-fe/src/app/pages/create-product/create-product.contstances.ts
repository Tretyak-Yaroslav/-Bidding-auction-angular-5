import {AbstractControl} from '@angular/forms/src/model';
import {ValidationErrors} from '@angular/forms/src/directives/validators';

export const DICTIONARY_GET_URL = '/api/v1/dictionary/platform/get';
export const PRODUCT_REGISTER_POST_URL = '/api/v1/product/register';
export const PRODUCT_IMAGE_PUT_URL = 'api/v1/product/update/image/';

export function onlyNumber(control: AbstractControl): ValidationErrors | null {
  return /[^0-9,.]+/.test(control.value) ? {onlyNumber: false} : null;
}
