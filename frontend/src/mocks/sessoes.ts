import { pautas } from './pautas';

export const session = {
   status: 'CREATED',
   votoComputado: false,
};

export const sessions = [
   {
      id: '1',
      pauta: pautas[0],
      status: 'OPEN_TO_VOTE',
      votoComputado: false,
   },
   {
      id: '2',
      pauta: pautas[1],
      status: 'CREATED',
      votoComputado: false,
   },
   {
      id: '3',
      pauta: pautas[2],
      status: 'CREATED',
      votoComputado: false,
   },
   {
      id: '4',
      pauta: pautas[3],
      status: 'CLOSED',
      votoComputado: false,
   },
   {
      id: '5',
      pauta: pautas[4],
      status: 'CANCELLED',
      votoComputado: false,
   },
];
