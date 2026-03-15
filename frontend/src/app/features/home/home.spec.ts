import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { of } from 'rxjs';
import { Router } from '@angular/router';
import { PautaStore } from '../pautas/pauta-store';
import { LoadingService } from '../../core/loading.service';
import { TokenStorageStore } from '../../core/auth/token-storage-store';

import { Home } from './home';

describe('Home', () => {
   let component: Home;
   let fixture: ComponentFixture<Home>;

   beforeEach(async () => {
      await TestBed.configureTestingModule({
         imports: [Home],
         providers: [
            {
               provide: PautaStore,
               useValue: {
                  buascarPautasComVotos: () => undefined,
                  items: () => [],
               },
            },
            {
               provide: LoadingService,
               useValue: {
                  loadingOn: () => undefined,
                  loadingOff: () => undefined,
               },
            },
            {
               provide: TokenStorageStore,
               useValue: {
                  loggedUser$: of({ id: 1, username: 'tester', roles: [] }),
               },
            },
            {
               provide: Router,
               useValue: { navigate: () => Promise.resolve(true) },
            },
         ],
         schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();

      fixture = TestBed.createComponent(Home);
      component = fixture.componentInstance;
      fixture.detectChanges();
   });

   it('should create', () => {
      expect(component).toBeTruthy();
   });
});
