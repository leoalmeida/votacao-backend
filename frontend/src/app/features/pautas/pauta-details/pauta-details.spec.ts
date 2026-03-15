import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';
import { AssociadoStore } from '../../associados/associado-store';
import { PautaStore } from '../pauta-store';

import { PautaDetails } from './pauta-details';

describe('PautaDetails', () => {
   let component: PautaDetails;
   let fixture: ComponentFixture<PautaDetails>;

   beforeEach(async () => {
      await TestBed.configureTestingModule({
         imports: [PautaDetails],
         providers: [
            {
               provide: ActivatedRoute,
               useValue: { queryParams: of({ type: 'new' }) },
            },
            {
               provide: AssociadoStore,
               useValue: { loggedUser$: of({ id: 1, nome: 'tester' }) },
            },
            {
               provide: PautaStore,
               useValue: { criarPauta: () => true },
            },
            {
               provide: Router,
               useValue: { navigate: () => Promise.resolve(true) },
            },
         ],
         schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();

      fixture = TestBed.createComponent(PautaDetails);
      component = fixture.componentInstance;
      fixture.detectChanges();
   });

   it('should create', () => {
      expect(component).toBeTruthy();
   });
});
