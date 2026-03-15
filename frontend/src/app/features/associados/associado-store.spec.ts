import { TestBed } from '@angular/core/testing';

import { AssociadoStore } from './associado-store';

describe('AssociadoService', () => {
   let service: AssociadoStore;

   beforeEach(() => {
      TestBed.configureTestingModule({});
      service = TestBed.inject(AssociadoStore);
   });

   it('should be created', () => {
      expect(service).toBeTruthy();
   });
});
