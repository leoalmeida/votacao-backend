import { inject, Injectable, signal } from '@angular/core';
import { environment } from '../../../environments/environment';
import { BehaviorSubject, Observable } from 'rxjs';
import { associados } from '../../shared/mocks/associados';
import { Associado } from '../../features/associados/associado';
import { TokenStorageStore } from './token-storage-store';

@Injectable({
   providedIn: 'root',
})
export class AuthStore {
   private baseUrl = '';
   private apiData = [...associados];
   private associadoList = signal<Associado[]>([]);
   private loggedUser = new BehaviorSubject<Associado>({} as Associado);

   private tokenStorageService = inject(TokenStorageStore);

   private mockHttpCall = <T>(result: T) => {
      return new Observable<T>((s) => {
         s.next(result);
         s.complete();
      });
   };

   loggedUser$ = this.loggedUser.asObservable();

   constructor() {
      this.baseUrl = environment.authApi || '/ms-auth/v1/auth'; // Default to a fallback URL if not define
      this.mockHttpCall<Associado[]>(this.apiData).subscribe((xs) =>
         this.associadoList.set(xs),
      );
   }

   login(username: string, password: string): Observable<Associado> {
      //return this.http.post<any>(`${this.baseUrl}/signin`, { username, password }, httpOptions);
      const selectedUser =
         this.apiData.find((item) => item.nome === username) ?? this.apiData[0];
      if (!password) {
         return this.mockHttpCall<Associado>({} as Associado);
      }

      this.mockHttpCall<Associado>(selectedUser).subscribe((user) => {
         this.loggedUser.next(user);
         this.tokenStorageService.saveJsonWebToken(user);
      });
      return this.loggedUser.asObservable();
   }

   logout(): void {
      //this.http.post<any>(`${this.baseUrl}/signout`, { }, httpOptions).subscribe();
      this.tokenStorageService.signOut();
   }

   register(
      username: string,
      email: string,
      telefone: string,
      password: string,
   ): Observable<Associado> {
      //return this.http.post<any>(`${this.baseUrl}/signup`, { username, email, password }, httpOptions);
      const item: number = this.apiData.push({
         id: Number(password),
         email: email,
         nome: username,
         telefone: telefone, // Placeholder, as telefone is not provided in the mock
         accessToken: 'abc123',
         votacaoAssociado: null,
      });
      return this.mockHttpCall<Associado>(this.apiData[item] as Associado);
   }
}
