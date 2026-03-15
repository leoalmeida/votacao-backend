import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient, HttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { vi } from 'vitest';

import { SessaoStore } from './sessao-store';
import { Sessao } from './sessao';

describe('SessaoStore', () => {
   let service: SessaoStore;
   let httpMock: HttpTestingController;
   let httpClient: HttpClient;

   beforeEach(() => {
      TestBed.configureTestingModule({
         providers: [SessaoStore, provideHttpClient(), provideHttpClientTesting()],
      });
      service = TestBed.inject(SessaoStore);
      httpMock = TestBed.inject(HttpTestingController);
      httpClient = TestBed.inject(HttpClient);
   });

   afterEach(() => {
      httpMock.verify();
   });

   it('should be created', () => {
      expect(service).toBeTruthy();
   });

   it('should fetch all sessions and update signal list', () => {
      const payload: Sessao[] = [{ id: 1, status: 'OPEN_TO_VOTE' }];

      service.getAll();

      const req = httpMock.expectOne('http://localhost:8082/ms-sessoes/v1/sessoes');
      expect(req.request.method).toBe('GET');
      req.flush(payload);

      expect(service.items().length).toBe(1);
      expect(service.items()[0].status).toBe('OPEN_TO_VOTE');
   });

   it('should fetch session by id', () => {
      const payload: Sessao = { id: 10, status: 'CLOSED' };
      let result: Sessao | undefined;

      service.getById(10).subscribe((value) => {
         result = value;
      });

      const req = httpMock.expectOne('http://localhost:8082/ms-sessoes/v1/sessoes/10');
      expect(req.request.method).toBe('GET');
      req.flush(payload);

      expect(result?.id).toBe(10);
      expect(result?.status).toBe('CLOSED');
   });

   it('should request start session and return true', () => {
      const putSpy = vi.spyOn(httpClient, 'put').mockReturnValue(of({}) as never);

      const result = service.startSession(5);

      expect(result).toBe(true);
      expect(putSpy).toHaveBeenCalledWith(
         'http://localhost:8082/ms-sessoes/v1/sessoes/5/iniciar',
         null,
      );
   });

   it('should request finalize session and return true', () => {
      const putSpy = vi.spyOn(httpClient, 'put').mockReturnValue(of({}) as never);

      const result = service.finalizeSession(7);

      expect(result).toBe(true);
      expect(putSpy).toHaveBeenCalledWith(
         'http://localhost:8082/ms-sessoes/v1/sessoes/7/finalizar',
         null,
      );
   });

   it('should request cancel session and return true', () => {
      const putSpy = vi.spyOn(httpClient, 'put').mockReturnValue(of({}) as never);

      const result = service.cancelSession(9);

      expect(result).toBe(true);
      expect(putSpy).toHaveBeenCalledWith(
         'http://localhost:8082/ms-sessoes/v1/sessoes/9/cancelar',
         null,
      );
   });

   it('should request add vote and return true', () => {
      const putSpy = vi.spyOn(httpClient, 'put').mockReturnValue(of({}) as never);

      const result = service.addSessionVote(12, 'SIM');

      expect(result).toBe(true);
      expect(putSpy).toHaveBeenCalledWith(
         'http://localhost:8082/ms-sessoes/v1/sessoes/12/votar/SIM',
         null,
      );
   });

   it('should request delete session and return true', () => {
      const deleteSpy = vi
         .spyOn(httpClient, 'delete')
         .mockReturnValue(of({}) as never);

      const result = service.removerAssociado(14);

      expect(result).toBe(true);
      expect(deleteSpy).toHaveBeenCalledWith(
         'http://localhost:8082/ms-sessoes/v1/sessoes/14',
      );
   });
});
