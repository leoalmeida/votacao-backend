import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthStore } from '../auth-store';
import { TokenStorageStore } from '../token-storage-store';

import { LoginComponent } from './login';

describe('LoginComponent', () => {
   let component: LoginComponent;
   let fixture: ComponentFixture<LoginComponent>;

   beforeEach(async () => {
      await TestBed.configureTestingModule({
         imports: [FormsModule, ReactiveFormsModule, LoginComponent],
         providers: [
            {
               provide: AuthStore,
               useValue: { login: () => ({ subscribe: () => undefined }) },
            },
            {
               provide: TokenStorageStore,
               useValue: { isAuthenticated: () => false },
            },
            {
               provide: Router,
               useValue: { navigate: () => Promise.resolve(true) },
            },
         ],
         schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();

      fixture = TestBed.createComponent(LoginComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
   });

   it('should create', () => {
      expect(component).toBeTruthy();
   });
});
