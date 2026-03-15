import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { Associado } from './associado';
//import { associados } from '../../../mocks/associados';
import { environment } from '../../../environments/environment';
@Injectable({
   providedIn: 'root',
})
export class AssociadoStore {
   private baseUrl = '/ms-associados/v1/associados';
   //private associadoList = signal<Associado[]>([]);
   private associadoList = new BehaviorSubject<Associado[]>([]);

   //private associado = signal<Associado>({} as Associado);
   private associado = new BehaviorSubject<Associado>({} as Associado);

   private http: HttpClient = inject(HttpClient);
   constructor() {
      this.baseUrl = environment.associadosApi;
   }

   items$ = this.associadoList.asObservable();
   //loggedUser = this.associado.asReadonly();
   loggedUser$ = this.associado.asObservable();

   //GET - "/"
   getAll(): Observable<Associado[]> {
      return this.http
         .get<Associado[]>(`${this.baseUrl}`)
         .pipe<Associado[]>(this.associadoList.pipe);
      //.subscribe(xs => this.associadoList.set(xs));
      //return this.associadoList.asReadonly();
   }

   //GET - "/{id}"
   getById(idAssociado: number): Observable<Associado> {
      return this.http
         .get<Associado>(`${this.baseUrl}/${idAssociado}`)
         .pipe<Associado>(this.associado.pipe);
   }

   //POST - "/"
   incluirAssociado(associado: Associado): Observable<Associado> {
      console.log(`Cadastrando novo associado: ${associado.nome}.`);
      return this.http.post<Associado>(`${this.baseUrl}`, associado);
   }

   //PUT - "/"
   alterarAssociado(associado: Associado): Observable<Associado> {
      console.log(`Alterando novo associado: ${associado.id}.`);
      return this.http.put<Associado>(
         `${this.baseUrl}/${associado.id}`,
         associado,
      );
   }
   //DELETE - "/"
   removerAssociado(idAssociado: number): void {
      console.log(`Removendo novo associado: ${idAssociado}.`);
      this.http.delete(`${this.baseUrl}/${idAssociado}`).subscribe({
         next: () =>
            console.log(`Associado ${idAssociado} removido com sucesso.`),
         error: (err) => console.log(err),
      });
   }
}
