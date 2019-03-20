import {Pipe, PipeTransform} from '@angular/core';
import {ServerTime} from '../services/authentication.service';

@Pipe({
  name: 'timeLeft'
})
export class TimeLeftPipe implements PipeTransform {

  constructor() {
  }

  transform(value: any, args?: any): any {
    let res = '';
    value = new Date(value);

    const now = ServerTime.getCurrent().getTime();
    const distance = value - now;
    if (distance <= 0) {
      return '0';
    }

    // Time calculations for days, hours, minutes and seconds
    const days = Math.floor(distance / (1000 * 60 * 60 * 24));
    const hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    const minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
    const seconds = Math.floor((distance % (1000 * 60)) / 1000);

    if (days !== 0) {
      res += days + 'd:';
    }
    if (hours !== 0) {
      res += hours + 'h:';
    }
    if (minutes !== 0) {
      res += minutes + 'm';
    }
    if (args != null && seconds !== 0) {
      res += seconds + 's';
    }

    return res;
  }

}
