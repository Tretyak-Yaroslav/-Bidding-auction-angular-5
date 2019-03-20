import { TestBed, inject } from '@angular/core/testing';

import { LotsService } from './lots.service';

describe('LotsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LotsService]
    });
  });

  it('should be created', inject([LotsService], (service: LotsService) => {
    expect(service).toBeTruthy();
  }));
});
