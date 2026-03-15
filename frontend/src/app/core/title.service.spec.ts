import { TestBed } from '@angular/core/testing';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { Subject } from 'rxjs';
import { vi } from 'vitest';

import { TitleService } from './title.service';

describe('TitleService', () => {
   let service: TitleService;
   let events$: Subject<unknown>;
   let titleSpy: { setTitle: ReturnType<typeof vi.fn> };

   const setup = (leafTitle?: string) => {
      events$ = new Subject<unknown>();
      titleSpy = { setTitle: vi.fn() };

      const leafRoute = {
         firstChild: null,
         outlet: 'primary',
         snapshot: { data: leafTitle ? { title: leafTitle } : {} },
      };

      const rootRoute = {
         firstChild: leafRoute,
         outlet: 'primary',
         snapshot: { data: {} },
      };

      TestBed.configureTestingModule({
         providers: [
            TitleService,
            { provide: Title, useValue: titleSpy },
            { provide: Router, useValue: { events: events$.asObservable() } },
            { provide: ActivatedRoute, useValue: rootRoute },
         ],
      });

      service = TestBed.inject(TitleService);
   };

   it('should set title from active child route', () => {
      setup('Home Page');

      service.setTitle();
      events$.next(new NavigationEnd(1, '/home', '/home'));

      expect(titleSpy.setTitle).toHaveBeenCalledWith('Home Page');
   });

   it('should fallback to default title when route has no title', () => {
      setup();

      service.setTitle();
      events$.next(new NavigationEnd(2, '/x', '/x'));

      expect(titleSpy.setTitle).toHaveBeenCalledWith('My App Title');
   });
});
