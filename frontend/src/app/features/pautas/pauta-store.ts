import { inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Pauta } from './pauta';

@Injectable({
   providedIn: 'root',
})
export class PautaStore {
   private baseUrl = '/ms-pautas/v1/pautas';
   private pautasList = signal<Pauta[]>([]);

   //private logger = inject(Logger);

   private http: HttpClient = inject(HttpClient);
   constructor() {
      this.baseUrl = environment.pautasApi;
   }

   items = this.pautasList.asReadonly();

   getAll(): void {
      this.http
         .get<Pauta[]>(`${this.baseUrl}`)
         .subscribe((xs) => this.pautasList.set(xs));
   }

   buascarPautasComVotos(idAssociado: number): void {
      this.http
         .get<Pauta[]>(`${this.baseUrl}/associado/${idAssociado}`)
         .subscribe((xs) => this.pautasList.set(xs));
   }

   //POST - "/"
   criarPauta(pauta: Pauta, iniciarSessao: boolean): boolean {
      console.log(
         `Solicitando nova votação: associado: ${pauta.idAssociado}, pauta: ${pauta.nome}.`,
      );
      this.http.post(`${this.baseUrl}&iniciarSessao=${iniciarSessao}`, pauta);
      return true;
   }
}
