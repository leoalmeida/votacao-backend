import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Voto } from './voto';

@Injectable({
   providedIn: 'root',
})
export class VotacaoStore {
   private baseUrl = '/ms-sessoes/v1/votos';

   private http: HttpClient = inject(HttpClient);
   constructor() {
      this.baseUrl = environment.votosApi;
   }

   //POST - "/"
   publicarVoto(idAssociado: number, idSessao: number, opcao: 'NAO' | 'SIM') {
      console.log(
         `Contabilizando voto para a sessão de votação: sessão: ${idSessao}. opcaoVoto: ${opcao}`,
      );
      const voto: Voto = { idAssociado, idSessao, opcao };
      this.http.post(`${this.baseUrl}`, voto).subscribe({
         next: (res) => console.log(res),
         error: (error) => console.error(error),
      });
      return true;
   }
}
