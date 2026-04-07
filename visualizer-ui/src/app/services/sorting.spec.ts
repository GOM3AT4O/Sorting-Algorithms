import { TestBed } from '@angular/core/testing';

import { Sorting } from './sorting';

describe('Sorting', () => {
  let service: Sorting;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Sorting);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
