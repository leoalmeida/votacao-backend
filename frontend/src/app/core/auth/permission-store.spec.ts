import { TestBed } from '@angular/core/testing';

import { PermissionStore } from './permission-store';
import { UserToken } from './user-token';

describe('PermissionService', () => {
   let service: PermissionStore;

   beforeEach(() => {
      TestBed.configureTestingModule({
         providers: [PermissionStore],
      });
      service = TestBed.inject(PermissionStore);
   });

   it('should be created', () => {
      expect(service).toBeTruthy();
   });

    it('should allow canActivate when id matches and role exists', () => {
      const user: UserToken = {
         id: 7,
         username: 'user',
         roles: ['ADMIN', 'USER'],
         permissions: [],
      };

      expect(service.canActivate(user, 7, 'ADMIN')).toBe(true);
   });

   it('should deny canActivate when id differs', () => {
      const user: UserToken = {
         id: 7,
         username: 'user',
         roles: ['ADMIN'],
         permissions: [],
      };

      expect(service.canActivate(user, 8, 'ADMIN')).toBe(false);
   });

   it('should deny canActivate when role is missing', () => {
      const user: UserToken = {
         id: 7,
         username: 'user',
         roles: ['USER'],
         permissions: [],
      };

      expect(service.canActivate(user, 7, 'ADMIN')).toBe(false);
   });

   it('should allow canMatch when role exists', () => {
      const user: UserToken = {
         id: 7,
         username: 'user',
         roles: ['ADMIN'],
         permissions: [],
      };

      expect(service.canMatch(user, 'ADMIN')).toBe(true);
   });

   it('should deny canMatch when role does not exist', () => {
      const user: UserToken = {
         id: 7,
         username: 'user',
         roles: ['USER'],
         permissions: [],
      };

      expect(service.canMatch(user, 'ADMIN')).toBe(false);
   });
});
