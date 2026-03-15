import { Component, inject, input, signal } from '@angular/core';
import { Routes } from '@angular/router';
import { routes } from '../../app.routes';
import { TokenStorageStore } from '../auth/token-storage-store';
import { MatToolbar } from '@angular/material/toolbar';
import { MatIconButton, MatButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { MatSidenav } from '@angular/material/sidenav';

@Component({
   selector: 'app-toolbar',
   templateUrl: './toolbar.html',
   styleUrl: './toolbar.css',
   imports: [MatToolbar, MatIconButton, MatIcon, MatButton, MatSidenav],
})
export class Toolbar {
   readonly title = input.required<string>();
   loggedUser = signal<string>('');
   routes: Routes = routes;
   opened = false;
   private tokenStorageService: TokenStorageStore =
      inject(TokenStorageStore);

   constructor() {
      this.tokenStorageService.loggedUser$.subscribe(
         (user) => this.loggedUser.set(user.username),
      );
   }

   showMenu(): void {
      this.opened = !this.opened;
   }
}
