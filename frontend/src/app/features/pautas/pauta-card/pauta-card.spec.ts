import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { of } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { SessaoStore } from '../sessao-store';
import { VotacaoStore } from '../../votacao/votacao-store';

import { PautaCard } from './pauta-card';

describe('SessionCard', () => {
   let component: PautaCard;
   let fixture: ComponentFixture<PautaCard>;

   beforeEach(async () => {
      await TestBed.configureTestingModule({
         imports: [PautaCard],
         providers: [
            {
               provide: MatDialog,
               useValue: {
                  open: () => ({ afterClosed: () => of(undefined) }),
               },
            },
            {
               provide: SessaoStore,
               useValue: {
                  startSession: () => true,
                  cancelSession: () => true,
               },
            },
            {
               provide: VotacaoStore,
               useValue: { publicarVoto: () => true },
            },
         ],
         schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();

      fixture = TestBed.createComponent(PautaCard);
      component = fixture.componentInstance;
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

   it('should create', () => {
      expect(component).toBeTruthy();
   });
});
