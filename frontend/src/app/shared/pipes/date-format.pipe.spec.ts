import { LOCALE_ID } from '@angular/core';
import { TestBed } from '@angular/core/testing';

import { DateFormatPipe } from './date-format.pipe';

describe('DateFormatPipe', () => {
   const createPipe = () =>
      TestBed.runInInjectionContext(() => new DateFormatPipe());

   beforeEach(() => {
      TestBed.configureTestingModule({
         providers: [{ provide: LOCALE_ID, useValue: 'en-US' }],
      });
   });

   it('should format a valid date string', () => {
      const pipe = createPipe();

      const result = pipe.transform('2026-01-01T03:04:05');

      expect(result).toBe('01/01/2026 03:04:05');
   });

   it('should throw for invalid date input', () => {
      const pipe = createPipe();

      expect(() => pipe.transform('not-a-date')).toThrow();
   });
});
