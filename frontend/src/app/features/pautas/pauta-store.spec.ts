import { TestBed } from '@angular/core/testing';

import { PautaStore } from './pauta-store';

describe('PautaStore', () => {
   let service: PautaStore;

   beforeEach(() => {
      TestBed.configureTestingModule({});
      service = TestBed.inject(PautaStore);
   });

   it('should be created', () => {
      expect(service).toBeTruthy();
   });
});
