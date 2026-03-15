import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { TemplateRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { SessaoStore } from '../sessao-store';
import { VotacaoStore } from '../../votacao/votacao-store';
import { vi } from 'vitest';

import { PautaCard } from './pauta-card';
import { Pauta } from '../pauta';

describe('SessionCard', () => {
   let component: PautaCard;
   let fixture: ComponentFixture<PautaCard>;
   let afterClosedResult: boolean | undefined;

   const openMock = vi.fn(() => ({
      afterClosed: () => of(afterClosedResult),
   }));
   const startSessionMock = vi.fn(() => true);
   const cancelSessionMock = vi.fn(() => true);
   const publicarVotoMock = vi.fn(() => true);

   const setPautaInput = (pauta: Pauta) => {
      fixture.componentRef.setInput('pauta', pauta);
      fixture.detectChanges();
   };

   beforeEach(async () => {
      afterClosedResult = undefined;
      openMock.mockClear();
      startSessionMock.mockClear();
      cancelSessionMock.mockClear();
      publicarVotoMock.mockClear();

      await TestBed.configureTestingModule({
         imports: [PautaCard],
         providers: [
            {
               provide: MatDialog,
               useValue: {
                  open: openMock,
               },
            },
            {
               provide: SessaoStore,
               useValue: {
                  startSession: startSessionMock,
                  cancelSession: cancelSessionMock,
               },
            },
            {
               provide: VotacaoStore,
               useValue: { publicarVoto: publicarVotoMock },
            },
         ],
      }).compileComponents();

      fixture = TestBed.createComponent(PautaCard);
      component = fixture.componentInstance;
      (component as any).dialog = {
         open: openMock,
      };
      (component as any).sessaoService = {
         startSession: startSessionMock,
         cancelSession: cancelSessionMock,
      };
      (component as any).votacaoStore = {
         publicarVoto: publicarVotoMock,
      };
      fixture.componentRef.setInput('loggedUser', {
         id: 1,
         username: 'tester',
         roles: [],
         permissions: [],
      });
      fixture.componentRef.setInput('pauta', {
         idAssociado: 1,
         nome: 'Pauta teste',
         descricao: 'Descricao',
         categoria: 'Geral',
         sessaoDto: { id: 1, status: 'CREATED' },
      });
      fixture.detectChanges();
   });

   afterEach(() => {
      vi.restoreAllMocks();
   });

   it('should create', () => {
      expect(component).toBeTruthy();
   });

   it('should publish vote when confirm dialog returns true', () => {
      afterClosedResult = true;
      const templateRef = {} as TemplateRef<unknown>;

      component.confirm(templateRef, 'SIM');

      expect(openMock).toHaveBeenCalled();
      expect(publicarVotoMock).toHaveBeenCalledWith(1, 1, 'SIM');
   });

   it('should reset toggle when confirm dialog returns false', () => {
      afterClosedResult = false;
      component.toggle = 'NAO';

      component.confirm({} as TemplateRef<unknown>, 'NAO');

      expect(component.toggle).toBeUndefined();
      expect(publicarVotoMock).not.toHaveBeenCalled();
   });

   it('should not open confirm dialog when pauta has no valid session', () => {
      const errorSpy = vi.spyOn(console, 'error').mockImplementation(() => undefined);
      setPautaInput({
         idAssociado: 1,
         nome: 'Sem sessao',
         descricao: 'Descricao',
         categoria: 'Geral',
      });

      component.confirm({} as TemplateRef<unknown>, 'SIM');

      expect(openMock).not.toHaveBeenCalled();
      expect(errorSpy).toHaveBeenCalled();
   });

   it('should start session on openDialog when status is CREATED and dialog closes with value', () => {
      afterClosedResult = true;
      setPautaInput({
         idAssociado: 1,
         nome: 'Sessao criada',
         descricao: 'Descricao',
         categoria: 'Geral',
         sessaoDto: { id: 15, status: 'CREATED' },
      });

      component.openDialog();

      expect(openMock).toHaveBeenCalled();
      expect(startSessionMock).toHaveBeenCalledWith(15);
   });

   it('should not start session on openDialog when dialog returns undefined', () => {
      afterClosedResult = undefined;
      setPautaInput({
         idAssociado: 1,
         nome: 'Sessao criada',
         descricao: 'Descricao',
         categoria: 'Geral',
         sessaoDto: { id: 16, status: 'CREATED' },
      });

      component.openDialog();

      expect(startSessionMock).not.toHaveBeenCalled();
   });

   it('should cancel session on cancelDialog when status is OPEN_TO_VOTE and dialog closes with value', () => {
      afterClosedResult = true;
      setPautaInput({
         idAssociado: 1,
         nome: 'Sessao aberta',
         descricao: 'Descricao',
         categoria: 'Geral',
         sessaoDto: { id: 22, status: 'OPEN_TO_VOTE' },
      });

      component.cancelDialog();

      expect(cancelSessionMock).toHaveBeenCalledWith(22);
   });

   it('should not cancel session on cancelDialog when dialog returns undefined', () => {
      afterClosedResult = undefined;
      setPautaInput({
         idAssociado: 1,
         nome: 'Sessao aberta',
         descricao: 'Descricao',
         categoria: 'Geral',
         sessaoDto: { id: 23, status: 'OPEN_TO_VOTE' },
      });

      component.cancelDialog();

      expect(cancelSessionMock).not.toHaveBeenCalled();
   });

   it('should not open cancel dialog when session is invalid', () => {
      const errorSpy = vi.spyOn(console, 'error').mockImplementation(() => undefined);
      setPautaInput({
         idAssociado: 1,
         nome: 'Sem sessao',
         descricao: 'Descricao',
         categoria: 'Geral',
      });

      component.cancelDialog();

      expect(openMock).not.toHaveBeenCalled();
      expect(errorSpy).toHaveBeenCalled();
   });
});
