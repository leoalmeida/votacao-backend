import { Component, inject, OnInit, signal, input } from '@angular/core';
import {
   FormControl,
   Validators,
   FormsModule,
   ReactiveFormsModule,
} from '@angular/forms';
import { AuthStore } from '../auth-store';
import { TokenStorageStore } from '../token-storage-store';
import { merge } from 'rxjs';
import { Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
   selector: 'app-login-form',
   templateUrl: './login.html',
   styleUrl: './login.css',
   standalone: true,
   imports: [
      MatCardModule,
      FormsModule,
      MatFormFieldModule,
      MatInputModule,
      ReactiveFormsModule,
      MatButtonModule,
   ],
})
export class LoginComponent implements OnInit {
   readonly username = new FormControl('', [Validators.required]);
   readonly password = new FormControl('', [Validators.required]);
   form: { username: string | null; password: string | null } = {
      username: null,
      password: null,
   };
   isLoggedIn = false;
   isLoginError = false;
   errorMessage = signal<string>('');
   roles: string[] = [];

   private authService: AuthStore = inject(AuthStore);
   private tokenStorage: TokenStorageStore = inject(TokenStorageStore);
   private navigator: Router = inject(Router);

   constructor() {
      merge(
         this.username.valueChanges,
         this.password.valueChanges,
         this.username.statusChanges,
         this.password.statusChanges,
      ).subscribe(() => this.updateErrorMessage());
   }

   updateErrorMessage() {
      if (this.password.invalid || this.username.invalid) {
         this.errorMessage.set('Dados inválidos.');
      } else {
         this.errorMessage.set('');
      }
   }

   ngOnInit() {
      this.reloadPage();
   }

   login() {
      if (this.username.valid && this.password.valid) {
         const username = this.username.value || '';
         const password = this.password.value || '';
         this.authService.login(username, password).subscribe((user) => {
            if (user) {
               this.reloadPage();
            }
         });
      }
   }

   reloadPage() {
      this.isLoggedIn = this.tokenStorage.isAuthenticated();
      if (this.isLoggedIn) {
         this.navigator.navigate(['/home']);
      }
   }

   readonly error = input('');
}
