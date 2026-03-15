import { inject, Injectable, signal } from '@angular/core';
import { Observable } from 'rxjs';
import { Sessao } from './sessao';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
   providedIn: 'root',
})
export class SessaoStore {
   private baseUrl = '/ms-sessoes/v1/sessoes';
   //private apiData = [...sessions];
   private sessionsList = signal<Sessao[]>([]);

   //private logger = inject(Logger);
   private http: HttpClient = inject(HttpClient);
   constructor() {
      this.baseUrl = environment.sessaoApi;
   }

   items = this.sessionsList.asReadonly();

   getAll(): void {
      this.http
         .get<Sessao[]>(`${this.baseUrl}`)
         .subscribe((xs) => this.sessionsList.set(xs));
   }

   getById(id: number): Observable<Sessao> {
      //this.logger.log(`Fetched.`);
      return this.http.get<Sessao>(`${this.baseUrl}/${id}`);
   }

   //PUT - "/{idSessao}/iniciar"
   startSession(idSessao: number) {
      console.log(`Iniciando sessão de votação: sessão: ${idSessao}.`);
      this.http.put(`${this.baseUrl}/${idSessao}/iniciar`, null);
      return true;
   }

   //PUT - "/{idSessao}/finalizar"
   finalizeSession(idSessao: number) {
      console.log(`Finalizando sessão de votação: sessão: ${idSessao}.`);
      this.http.put(`${this.baseUrl}/${idSessao}/finalizar`, null);
      return true;
   }

   //PUT - "/{idSessao}/cancelar"
   cancelSession(idSessao: number) {
      console.log(`Cancelando sessão de votação: sessão: ${idSessao}.`);
      this.http.put(`${this.baseUrl}/${idSessao}/cancelar`, null);
      return true;
   }

   //PUT - "/{idSessao}/votar"
   addSessionVote(idSessao: number, opcaoVoto: string) {
      console.log(
         `Contabilizando voto para a sessão de votação: sessão: ${idSessao}. opcaoVoto: ${opcaoVoto}`,
      );
      this.http.put(`${this.baseUrl}/${idSessao}/votar/${opcaoVoto}`, null);
      return true;
   }

   //DELETE - "/"
   removerAssociado(idSessao: number): boolean {
      console.log(`Removendo sessão: ${idSessao}.`);
      this.http.delete(`${this.baseUrl}/${idSessao}`);
      return true;
   }
}
