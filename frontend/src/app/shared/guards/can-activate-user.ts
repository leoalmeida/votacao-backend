import { CanActivateFn } from '@angular/router';
import { inject } from '@angular/core';
import { TokenStorageStore } from '../../core/auth/token-storage-store';

export const canActivateUser: CanActivateFn = () => {
   const tokenService = inject(TokenStorageStore);
   return tokenService.isAuthenticated();
};
