import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import {
   MatDialogRef,
   MatDialogModule,
} from '@angular/material/dialog';
import { CdkScrollableModule } from '@angular/cdk/scrolling';
import { MatButtonModule } from '@angular/material/button';

@Component({
   selector: 'app-simple-dialog',
   changeDetection: ChangeDetectionStrategy.OnPush,
   templateUrl: './simple-dialog.html',
   styleUrl: './simple-dialog.css',
   standalone: true,
   imports: [
      MatDialogModule,
      CdkScrollableModule,
      MatButtonModule
   ],
})
export class SimpleDialog {
   readonly dialogRef = inject(MatDialogRef<SimpleDialog>);
}
