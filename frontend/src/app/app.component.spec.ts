import { TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { AppComponent } from './app.component';
import { TitleService } from './core/title.service';

describe('AppComponent', () => {
   beforeEach(async () => {
      await TestBed.configureTestingModule({
         imports: [AppComponent],
         providers: [
            {
               provide: TitleService,
               useValue: { setTitle: () => undefined },
            },
         ],
         schemas: [NO_ERRORS_SCHEMA],
      }).compileComponents();
   });

   it('should create the app', () => {
      const fixture = TestBed.createComponent(AppComponent);
      const app = fixture.componentInstance;
      expect(app).toBeTruthy();
   });

   it('should render title', () => {
      const fixture = TestBed.createComponent(AppComponent);
      const app = fixture.componentInstance as AppComponent & {
         title: () => string;
      };
      expect(app.title()).toBe('Votação de Pautas');
   });
});
