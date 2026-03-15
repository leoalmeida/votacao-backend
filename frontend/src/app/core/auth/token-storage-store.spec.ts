import { TestBed } from '@angular/core/testing';

import { TokenStorageStore } from './token-storage-store';

describe('TokenStorageStore', () => {
   let service: TokenStorageStore;

   beforeEach(() => {
      TestBed.configureTestingModule({});
      service = TestBed.inject(TokenStorageStore);
   });

   it('should be created', () => {
      expect(service).toBeTruthy();
   });
});
