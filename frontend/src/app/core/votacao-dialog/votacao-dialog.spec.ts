import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

import { VotacaoDialog } from './votacao-dialog';

describe('VotacaoDialog', () => {
   let component: VotacaoDialog;
   let fixture: ComponentFixture<VotacaoDialog>;

   beforeEach(async () => {
      await TestBed.configureTestingModule({
         imports: [VotacaoDialog],
         providers: [
            {
               provide: MatDialogRef,
               useValue: { close: () => undefined },
            },
            {
               provide: MAT_DIALOG_DATA,
               useValue: {
                  pauta: {
                     idAssociado: 1,
                     nome: 'Pauta teste',
                     descricao: 'Descricao',
                     categoria: 'Geral',
                  },
                  loggedUser: 'tester',
                  opcaoEscolhida: 'SIM',
               },
            },
         ],
         schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();

      fixture = TestBed.createComponent(VotacaoDialog);
      component = fixture.componentInstance;
      fixture.detectChanges();
   });

   it('should create', () => {
      expect(component).toBeTruthy();
   });
});
