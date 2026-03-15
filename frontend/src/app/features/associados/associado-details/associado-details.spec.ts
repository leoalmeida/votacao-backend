import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';
import { AssociadoStore } from '../associado-store';

import { AssociadoDetails } from './associado-details';

describe('AssociadoDetails', () => {
   let component: AssociadoDetails;
   let fixture: ComponentFixture<AssociadoDetails>;

   beforeEach(async () => {
      await TestBed.configureTestingModule({
         imports: [AssociadoDetails],
         providers: [
            {
               provide: ActivatedRoute,
               useValue: { queryParams: of({ type: 'new' }) },
            },
            {
               provide: AssociadoStore,
               useValue: {
                  loggedUser$: of({ id: 1, nome: 'tester' }),
                  incluirAssociado: () => of({ id: 1 }),
               },
            },
            {
               provide: Router,
               useValue: { navigate: () => Promise.resolve(true) },
            },
         ],
         schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();

      fixture = TestBed.createComponent(AssociadoDetails);
      component = fixture.componentInstance;
      fixture.detectChanges();
   });

   it('should create', () => {
      expect(component).toBeTruthy();
   });
});
