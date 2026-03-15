import { Sessao } from "./sessao";

export interface Pauta {
   id?: number;
   nome: string;
   descricao: string;
   categoria: string;
   idAssociado: number;
   sessaoDto?: Sessao | null;
}
