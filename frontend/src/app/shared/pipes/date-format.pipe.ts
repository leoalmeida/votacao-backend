import { inject, LOCALE_ID, Pipe, PipeTransform } from '@angular/core';
import { DatePipe } from '@angular/common';

@Pipe({
   name: 'dateFormatPipe',
   standalone: true,
})
export class DateFormatPipe implements PipeTransform {
   private locale = inject(LOCALE_ID);

   transform(value: string) {
      const datePipe = new DatePipe(this.locale);
      value = datePipe.transform(value, 'dd/MM/yyyy HH:mm:ss') || '';
      return value;
   }
}
