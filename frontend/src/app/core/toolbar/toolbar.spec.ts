import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { of } from 'rxjs';
import { TokenStorageStore } from '../auth/token-storage-store';

import { Toolbar } from './toolbar';

describe('Toolbar', () => {
   let component: Toolbar;
   let fixture: ComponentFixture<Toolbar>;

   beforeEach(async () => {
      await TestBed.configureTestingModule({
         imports: [Toolbar],
         providers: [
            {
               provide: TokenStorageStore,
               useValue: { loggedUser$: of({ username: 'tester' }) },
            },
         ],
         schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();

      fixture = TestBed.createComponent(Toolbar);
      component = fixture.componentInstance;
      fixture.componentRef.setInput('title', 'Votacao de Pautas');
      fixture.detectChanges();
   });

   it('should create', () => {
      expect(component).toBeTruthy();
   });
});
