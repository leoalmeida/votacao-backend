import { TestBed } from '@angular/core/testing';

import { TokenStorageStore } from './token-storage-store';
import { Associado } from '../../features/associados/associado';
import { UserToken } from './user-token';

describe('TokenStorageStore', () => {
   let service: TokenStorageStore;

   const buildToken = (payload: UserToken) => {
      const header = btoa(JSON.stringify({ alg: 'HS256', typ: 'JWT' }));
      const body = btoa(JSON.stringify(payload));
      return `${header}.${body}.signature`;
   };

   beforeEach(() => {
      TestBed.configureTestingModule({});
      service = TestBed.inject(TokenStorageStore);
      window.sessionStorage.clear();
   });

   it('should be created', () => {
      expect(service).toBeTruthy();
   });

   it('should save jwt token and authenticate user', () => {
      const payload: UserToken = {
         id: 10,
         username: 'tester',
         roles: ['ADMIN'],
         permissions: ['READ'],
      };
      const associado = {
         id: 10,
         nome: 'tester',
         email: 'tester@email.com',
         telefone: '123',
         accessToken: buildToken(payload),
      } as Associado;

      service.saveJsonWebToken(associado);

      expect(service.getToken()).toBe(associado.accessToken ?? null);
      expect(service.isAuthenticated()).toBe(true);

      let emittedUser: UserToken | undefined;
      service.loggedUser$.subscribe((value) => {
         emittedUser = value;
      });
      expect(emittedUser?.id).toBe(10);
      expect(emittedUser?.roles).toContain('ADMIN');
   });

   it('should ignore jwt save when accessToken is missing', () => {
      const associado = {
         id: 2,
         nome: 'no-token',
         email: 'x@y.com',
         telefone: '123',
      } as Associado;

      service.saveJsonWebToken(associado);

      expect(service.getToken()).toBeNull();
      expect(service.isAuthenticated()).toBe(false);
   });

   it('should save and get user from session storage', () => {
      const associado = {
         id: 3,
         nome: 'saved-user',
         email: 'saved@email.com',
         telefone: '123',
      } as Associado;

      service.saveUser(associado);
      const user = service.getUser();

      expect(user.id).toBe(3);
      expect(user.nome).toBe('saved-user');
   });

   it('should return empty object when user is not in storage', () => {
      const user = service.getUser();

      expect(user).toEqual({});
   });

   it('should clear session and unauthenticate on signOut', () => {
      window.sessionStorage.setItem('auth-token', 'abc');
      window.sessionStorage.setItem('user', JSON.stringify({ id: 1 }));

      service.signOut();

      expect(window.sessionStorage.getItem('auth-token')).toBeNull();
      expect(window.sessionStorage.getItem('user')).toBeNull();
      expect(service.isAuthenticated()).toBe(false);
   });
});
