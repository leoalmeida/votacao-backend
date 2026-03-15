import { Pauta } from './pauta';

export interface Votacao {
   pauta: Pauta;
   loggedUser: string;
   opcaoEscolhida: '' | 'NAO' | 'SIM';
}
