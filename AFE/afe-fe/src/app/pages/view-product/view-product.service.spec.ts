import { TestBed, inject } from '@angular/core/testing';

import { ViewProductService } from './view-product.service';

describe('ViewProductService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ViewProductService]
    });
  });

  it('should be created', inject([ViewProductService], (service: ViewProductService) => {
    expect(service).toBeTruthy();
  }));
});
