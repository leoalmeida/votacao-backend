import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient, HttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { vi } from 'vitest';

import { PautaStore } from './pauta-store';
import { Pauta } from './pauta';

describe('PautaStore', () => {
   let service: PautaStore;
   let httpMock: HttpTestingController;
   let httpClient: HttpClient;

   beforeEach(() => {
      TestBed.configureTestingModule({
         providers: [PautaStore, provideHttpClient(), provideHttpClientTesting()],
      });
      service = TestBed.inject(PautaStore);
      httpMock = TestBed.inject(HttpTestingController);
      httpClient = TestBed.inject(HttpClient);
   });

   afterEach(() => {
      httpMock.verify();
   });

   it('should be created', () => {
      expect(service).toBeTruthy();
   });

   it('should fetch all pautas and update signal list', () => {
      const payload: Pauta[] = [
         {
            id: 1,
            nome: 'Pauta 1',
            descricao: 'Descricao',
            categoria: 'CATEGORIA',
            idAssociado: 10,
         },
      ];

      service.getAll();

      const req = httpMock.expectOne('http://localhost:8082/ms-pautas/v1/pautas');
      expect(req.request.method).toBe('GET');
      req.flush(payload);

      expect(service.items().length).toBe(1);
      expect(service.items()[0].id).toBe(1);
   });

   it('should fetch pautas by associado and update signal list', () => {
      const payload: Pauta[] = [
         {
            id: 2,
            nome: 'Pauta 2',
            descricao: 'Descricao 2',
            categoria: 'FINANCAS',
            idAssociado: 99,
         },
      ];

      service.buascarPautasComVotos(99);

      const req = httpMock.expectOne(
         'http://localhost:8082/ms-pautas/v1/pautas/associado/99',
      );
      expect(req.request.method).toBe('GET');
      req.flush(payload);

      expect(service.items().length).toBe(1);
      expect(service.items()[0].idAssociado).toBe(99);
   });

   it('should call post and return true when creating pauta', () => {
      const postSpy = vi.spyOn(httpClient, 'post').mockReturnValue(of({}) as never);

      const result = service.criarPauta(
         {
            nome: 'Nova pauta',
            descricao: 'Descricao',
            categoria: 'GERAL',
            idAssociado: 7,
         },
         true,
      );

      expect(result).toBe(true);
      expect(postSpy).toHaveBeenCalledWith(
         'http://localhost:8082/ms-pautas/v1/pautas&iniciarSessao=true',
         expect.any(Object),
      );
   });
});
