import { TestBed } from '@angular/core/testing';

import { VotacaoStore } from './votacao-store';

describe('VotacaoService', () => {
   let service: VotacaoStore;

   beforeEach(() => {
      TestBed.configureTestingModule({});
      service = TestBed.inject(VotacaoStore);
   });

   it('should be created', () => {
      expect(service).toBeTruthy();
   });
});
