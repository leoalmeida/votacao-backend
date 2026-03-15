import { Component, inject, model } from '@angular/core';
import {
   MAT_DIALOG_DATA,
   MatDialogRef,
   MatDialogTitle,
   MatDialogContent,
   MatDialogActions,
   MatDialogClose,
} from '@angular/material/dialog';
import { Votacao } from '../../features/pautas/votacao';
import { CdkScrollable } from '@angular/cdk/scrolling';
import {
   MatButtonToggleGroup,
   MatButtonToggle,
} from '@angular/material/button-toggle';
import { FormsModule } from '@angular/forms';
import { MatButton } from '@angular/material/button';

@Component({
   selector: 'app-votacao-dialog',
   templateUrl: './votacao-dialog.html',
   styleUrl: './votacao-dialog.css',
   imports: [
      MatDialogTitle,
      CdkScrollable,
      MatDialogContent,
      MatButtonToggleGroup,
      FormsModule,
      MatButtonToggle,
      MatDialogActions,
      MatButton,
      MatDialogClose,
   ],
})
export class VotacaoDialog {
   readonly dialogRef = inject(MatDialogRef<VotacaoDialog>);
   readonly data = inject<Votacao>(MAT_DIALOG_DATA);
   readonly opcaoEscolhida = model(this.data.opcaoEscolhida);

   onNoClick(): void {
      this.dialogRef.close();
   }
}
