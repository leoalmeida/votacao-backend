import { Routes } from '@angular/router';

import { canActivateUser } from './shared/guards/can-activate-user';

export const routes: Routes = [
   {
      path: 'login',
      loadComponent: () =>
         import('./core/auth/login/login').then((m) => m.LoginComponent),
      data: { title: 'Login' },
   },
   {
      path: 'home',
      loadComponent: () => import('./features/home/home').then((m) => m.Home),
      data: { title: 'Home Page' },
      canActivate: [canActivateUser],
   },
   {
      path: 'admin',
      loadComponent: () => import('./features/admin/admin').then((m) => m.Admin),
      data: { title: 'Admin Page' },
      canActivate: [canActivateUser],
   },
   {
      path: 'pauta/:type',
      loadComponent: () =>
         import('./features/pautas/pauta-details/pauta-details').then(
            (m) => m.PautaDetails,
         ),
      data: { title: 'Nova pauta' },
      canActivate: [canActivateUser],
   },
   {
      path: 'associado/:type',
      loadComponent: () =>
         import('./features/associados/associado-details/associado-details').then(
            (m) => m.AssociadoDetails,
         ),
      data: { title: 'Novo associado' },
      canActivate: [canActivateUser],
   },
   { path: '**', redirectTo: 'login' },
];
