import { Component, inject, TemplateRef, input } from '@angular/core';
import {
   MatDialog,
   MatDialogModule,
} from '@angular/material/dialog';
import { SimpleDialog } from '../simple-dialog/simple-dialog';
import { SessaoStore } from '../sessao-store';
import { Pauta } from '../pauta';
import { VotacaoStore } from '../../votacao/votacao-store';
import { UserToken } from '../../../core/auth/user-token';
import {
   MatCardModule,
} from '@angular/material/card';
import { NgClass } from '@angular/common';
import { MatDividerModule } from '@angular/material/divider';
import { MatButtonModule } from '@angular/material/button';
import {
   MatButtonToggleModule,
} from '@angular/material/button-toggle';
import { FormsModule } from '@angular/forms';
import { CdkScrollableModule } from '@angular/cdk/scrolling';
import { DateFormatPipe } from 'src/app/shared/pipes/date-format.pipe';
//import { DateFormatPipe } from '../../pipes/date-format.pipe';

@Component({
   selector: 'app-pauta-card',
   templateUrl: './pauta-card.html',
   styleUrl: './pauta-card.css',
   standalone: true,
   imports: [
      MatCardModule,
      NgClass,
      MatDividerModule,
      MatButtonModule,
      MatButtonToggleModule,
      FormsModule,
      MatDialogModule,
      CdkScrollableModule,
      DateFormatPipe
   ],
})
export class PautaCard {
   readonly pauta = input.required<Pauta>();
   readonly loggedUser = input.required<UserToken>();
   toggle?: 'SIM' | 'NAO';
   idAssociado = 0;
   readonly dialog = inject(MatDialog);

   private sessaoService: SessaoStore = inject(SessaoStore);
   private votacaoStore: VotacaoStore = inject(VotacaoStore);

   confirm(dialogRef: TemplateRef<unknown>, opcaoEscolhida: 'NAO' | 'SIM') {
      const pauta = this.pauta();
      if (!pauta.sessaoDto || !pauta.sessaoDto.id) {
         console.error('Pauta não possui uma sessão válida.');
         return;
      }
      const refOpen = this.dialog.open(dialogRef, {
         width: '250px',
         enterAnimationDuration: '0ms',
         exitAnimationDuration: '0ms',
      });
      const sessaoId: number = pauta.sessaoDto.id;
      refOpen.afterClosed().subscribe((result) => {
         console.log('[Confirm]', result);
         if (result == true) {
            this.votacaoStore.publicarVoto(
               this.loggedUser().id,
               sessaoId,
               opcaoEscolhida,
            );
         } else {
            this.toggle = undefined;
         }
      });
   }

   openDialog(): void {
      const pauta = this.pauta();
      if (!pauta.sessaoDto || !pauta.sessaoDto.id) {
         console.error('Sessão não encontrada para a pauta.');
         return;
      }
      if (pauta.sessaoDto.status === 'CREATED') {
         const dialogRef = this.dialog.open(SimpleDialog, {
            width: '250px',
            enterAnimationDuration: '0ms',
            exitAnimationDuration: '0ms',
         });
         const sessaoId: number = pauta.sessaoDto.id;
         dialogRef.afterClosed().subscribe((result) => {
            console.log('The dialog was closed' + result);
            if (result !== undefined) {
               this.sessaoService.startSession(sessaoId);
            }
         });
      }
   }

   cancelDialog(): void {
      const pauta = this.pauta();
      if (!pauta.sessaoDto || !pauta.sessaoDto.id) {
         console.error('Sessão não encontrada para a pauta.');
         return;
      }
      if (pauta.sessaoDto.status === 'OPEN_TO_VOTE') {
         const sessaoId: number = pauta.sessaoDto.id;
         const dialogRef = this.dialog.open(SimpleDialog, {
            width: '250px',
            enterAnimationDuration: '0ms',
            exitAnimationDuration: '0ms',
         });
         dialogRef.afterClosed().subscribe((result) => {
            console.log('The dialog was closed' + result);
            if (result !== undefined) {
               this.sessaoService.cancelSession(sessaoId);
            }
         });
      }
   }
}
