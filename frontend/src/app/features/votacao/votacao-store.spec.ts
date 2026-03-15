import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { vi } from 'vitest';

import { VotacaoStore } from './votacao-store';

describe('VotacaoService', () => {
   let service: VotacaoStore;
   let httpMock: HttpTestingController;

   beforeEach(() => {
      TestBed.configureTestingModule({
         providers: [VotacaoStore, provideHttpClient(), provideHttpClientTesting()],
      });
      service = TestBed.inject(VotacaoStore);
      httpMock = TestBed.inject(HttpTestingController);
   });

   afterEach(() => {
      httpMock.verify();
      vi.restoreAllMocks();
   });

   it('should be created', () => {
      expect(service).toBeTruthy();
   });

   it('should publish vote and post expected payload', () => {
      const logSpy = vi.spyOn(console, 'log').mockImplementation(() => undefined);

      const result = service.publicarVoto(22, 33, 'SIM');

      const req = httpMock.expectOne('http://localhost:8082/ms-sessoes/v1/votos');
      expect(req.request.method).toBe('POST');
      expect(req.request.body).toEqual({
         idAssociado: 22,
         idSessao: 33,
         opcao: 'SIM',
      });
      req.flush({ id: 1 });

      expect(result).toBe(true);
      expect(logSpy).toHaveBeenCalled();
   });

   it('should log errors from vote publication request', () => {
      const errorSpy = vi.spyOn(console, 'error').mockImplementation(() => undefined);

      service.publicarVoto(1, 2, 'NAO');

      const req = httpMock.expectOne('http://localhost:8082/ms-sessoes/v1/votos');
      req.flush('failed', { status: 500, statusText: 'Server Error' });

      expect(errorSpy).toHaveBeenCalled();
   });
});
