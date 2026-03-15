import { Voto } from '../votacao/voto';

export interface Sessao {
   id: number;
   status: string;
   totalizadores?: [];
   resultado?: '' | 'NAO' | 'SIM';
   dataFimSessao?: string;
   dataInicioSessao?: string;
   votoAssociado?: Voto | null;
}
