import { TestBed } from '@angular/core/testing';

import { SessaoStore } from './sessao-store';

describe('SessaoStore', () => {
   let service: SessaoStore;

   beforeEach(() => {
      TestBed.configureTestingModule({});
      service = TestBed.inject(SessaoStore);
   });

   it('should be created', () => {
      expect(service).toBeTruthy();
   });
});
