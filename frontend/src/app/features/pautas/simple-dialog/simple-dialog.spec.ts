import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

import { SimpleDialog } from './simple-dialog';

describe('SimpleDialog', () => {
   let component: SimpleDialog;
   let fixture: ComponentFixture<SimpleDialog>;

   beforeEach(async () => {
      await TestBed.configureTestingModule({
         imports: [SimpleDialog],
         providers: [
            {
               provide: MatDialogRef,
               useValue: { close: () => undefined },
            },
         ],
         schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();

      fixture = TestBed.createComponent(SimpleDialog);
      component = fixture.componentInstance;
      fixture.detectChanges();
   });

   it('should create', () => {
      expect(component).toBeTruthy();
   });
});
