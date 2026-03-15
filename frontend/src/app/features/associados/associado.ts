import { Voto } from '../votacao/voto';

export interface Associado {
   id: number;
   email: string;
   nome: string;
   telefone: string;
   accessToken?: string;
   votacaoAssociado?: Map<number, Voto> | null;
}
