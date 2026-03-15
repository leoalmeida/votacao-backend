import { Component, computed, inject, signal } from '@angular/core';
import { PautaStore } from '../pautas/pauta-store';
import { Router } from '@angular/router';
import { LoadingService } from '../../core/loading.service';
import { TokenStorageStore } from '../../core/auth/token-storage-store';
import { UserToken } from '../../core/auth/user-token';
import { Searchbar } from '../../core/searchbar/searchbar';
import { PautaCard } from '../pautas/pauta-card/pauta-card';
import { Pauta } from '../pautas/pauta';

@Component({
   selector: 'app-home',
   templateUrl: './home.html',
   styleUrl: './home.css',
   standalone: true,
   imports: [Searchbar, PautaCard],
})
export class Home {
   protected loggedUser = signal({} as UserToken);
   pautaList: Pauta[] = [];
   searchQuery = signal<string>('');
   private pautaService: PautaStore = inject(PautaStore);
   private loadingService: LoadingService = inject(LoadingService);
   private tokenStorageService: TokenStorageStore =
      inject(TokenStorageStore);
   private navigator: Router = inject(Router);

   constructor() {
      try {
         this.loadingService.loadingOn();
         this.tokenStorageService.loggedUser$.subscribe((user) => {
            this.loggedUser.set(user);
            this.pautaService.buascarPautasComVotos(user.id);
         });
      } catch (error) {
         console.log(error);
      } finally {
         this.loadingService.loadingOff();
      }
   }

   filteredPautaList = computed(() => {
      try {
         this.loadingService.loadingOn();
         const normalizedQuery = this.searchQuery()
            .normalize('NFD')
            .replace(/[\u0300-\u036f]/g, '')
            .toLowerCase();
         return this.pautaService
            .items()
            .filter((x) => x?.nome.toLowerCase().includes(normalizedQuery));
      } catch (error) {
         console.log(error);
         return;
      } finally {
         this.loadingService.loadingOff();
      }
   });

   handleMessage(message: string) {
      console.log('Received message from child:', message);
      this.searchQuery.set(message);
   }

   onCreatePauta() {
      this.navigator.navigate(['pauta-details'], {
         queryParams: { type: 'new' },
      });
   }
}
