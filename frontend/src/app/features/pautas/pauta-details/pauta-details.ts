import { Component, inject, signal } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AssociadoStore } from '../../associados/associado-store';
import { Associado } from '../../associados/associado';
import { Pauta } from '../pauta';
import { PautaStore } from '../pauta-store';
import { MatInputModule } from '@angular/material/input';

@Component({
   selector: 'app-pauta-details',
   templateUrl: './pauta-details.html',
   styleUrls: ['./pauta-details.css'],
   standalone: true,
   imports: [MatInputModule],
})
export class PautaDetails {
   nome = new FormControl('');
   descricao = new FormControl('');
   categoria = new FormControl('');
   private activeRoute: ActivatedRoute = inject(ActivatedRoute);
   private associadoService: AssociadoStore = inject(AssociadoStore);
   private pautaStore: PautaStore = inject(PautaStore);
   private navegador: Router = inject(Router);
   protected loggedUser = signal({} as Associado);

   constructor() {
      this.activeRoute.queryParams.subscribe((params) => {
         const type = params['type'];
         if (type === 'new') {
            // Initialize form for creating a new pauta
            this.nome.setValue('');
            this.descricao.setValue('');
         } else {
            // Handle other types if necessary
            this.navegador.navigate(['home']);
         }
      });
      this.associadoService.loggedUser$.subscribe((value) =>
         this.loggedUser.set(value),
      );
   }

   onSubmit() {
      const pauta: Pauta = {
         nome: this.nome.value || '',
         descricao: this.descricao.value || '',
         categoria: this.categoria.value || '',
         idAssociado: this.loggedUser().id || 0, // Example ID, replace with actual logic
      };

      // Call service to create pauta
      this.pautaStore.criarPauta(pauta, true);
      console.log('Pauta created:', pauta);
      this.navegador.navigate(['home']);
   }
}
