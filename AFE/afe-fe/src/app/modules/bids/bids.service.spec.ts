import { TestBed, inject } from '@angular/core/testing';

import { BidsService } from './bids.service';

describe('BidsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BidsService]
    });
  });

  it('should be created', inject([BidsService], (service: BidsService) => {
    expect(service).toBeTruthy();
  }));
});
