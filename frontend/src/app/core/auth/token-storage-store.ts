import { Injectable, signal } from '@angular/core';
import { Associado } from '../../features/associados/associado';
import { BehaviorSubject } from 'rxjs';
import { UserToken } from './user-token';

@Injectable({
   providedIn: 'root',
})
export class TokenStorageStore {
   private userToken = new BehaviorSubject<UserToken>({} as UserToken);
   private loggedIn = signal<boolean>(false);

   isAuthenticated = this.loggedIn.asReadonly();
   loggedUser = this.userToken.getValue();
   loggedUser$ = this.userToken.asObservable();

   signOut(): void {
      window.sessionStorage.clear();
      this.userToken.next({} as UserToken);
      this.loggedIn.set(false);
   }

   public saveJsonWebToken(associado: Associado): void {
      if (associado.accessToken) {
         window.sessionStorage.removeItem('auth-token'); // Clear previous token
         window.sessionStorage.setItem('auth-token', associado.accessToken); // Save token in session storage

         const userToken: UserToken = JSON.parse(
            atob(associado.accessToken.split('.')[1]),
         );
         this.userToken.next(userToken);
         this.loggedIn.set(true);
      }
   }

   public getToken(): string | null {
      return window.sessionStorage.getItem('auth-token');
   }
   public saveUser(user: Associado): void {
      window.sessionStorage.removeItem('user'); // Clear previous user
      window.sessionStorage.setItem('user', JSON.stringify(user));
   }
   public getUser(): Associado {
      const user = window.sessionStorage.getItem('user');
      return user ? JSON.parse(user) : ({} as Associado);
   }
}
