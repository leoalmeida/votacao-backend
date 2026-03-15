import { Component, inject, OnInit, signal } from '@angular/core';
import { TitleService } from './core/title.service';
import { Toolbar } from './core/toolbar/toolbar';
import { RouterOutlet } from '@angular/router';
import { LoadingIndicatorComponent } from './core/loading-indicator/loading-indicator';

@Component({
   selector: 'app-root',
   templateUrl: './app.component.html',
   styleUrl: './app.component.css',
   imports: [Toolbar, RouterOutlet, LoadingIndicatorComponent],
})
export class AppComponent implements OnInit {
   protected readonly title = signal('Votação de Pautas');
   private roles: string[] = [];
   isloggedIn = false;
   showAdminBoard = false;
   showModeratorBoard = false;

   private titleService: TitleService = inject(TitleService);

   ngOnInit(): void {
      this.titleService.setTitle();
   }

   updateViewByRole(): void {
      this.showAdminBoard = this.roles.includes('ADMIN');
      this.showModeratorBoard = this.roles.includes('MODERATOR');
   }
}
