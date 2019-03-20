import {SortDirection} from '../models';

export function sortArray(array: any[], variable: string, dir?: SortDirection, iterator?: (a, b) => number): any[] {
  const sortF = iterator ? iterator : (a, b) => a - b;
  if (array) {
    return array.sort((a, b) => {
      const res = sortF(a[variable], b[variable]);
      return dir === SortDirection.DESC ? res * -1 : res;
    });
  }
  return null;
}
