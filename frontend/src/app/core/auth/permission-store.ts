import { Injectable } from '@angular/core';

import { UserToken } from './user-token';

@Injectable()
export class PermissionStore {
   canActivate(currentUser: UserToken, userId: number, role: string): boolean {
      return currentUser.id === userId && currentUser.roles.includes(role);
   }
   canMatch(currentUser: UserToken, role: string): boolean {
      return currentUser.roles.includes(role);
   }
}
