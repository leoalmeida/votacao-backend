import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouteConfigLoadEnd, RouteConfigLoadStart, Router } from '@angular/router';
import { Subject } from 'rxjs';
import { vi } from 'vitest';

import { LoadingIndicatorComponent } from './loading-indicator';
import { LoadingService } from '../loading.service';

describe('LoadingIndicatorComponent', () => {
   let fixture: ComponentFixture<LoadingIndicatorComponent>;
   let component: LoadingIndicatorComponent;
   let routerEvents$: Subject<unknown>;
   let loadingService: LoadingService;

   beforeEach(async () => {
      routerEvents$ = new Subject<unknown>();

      await TestBed.configureTestingModule({
         imports: [LoadingIndicatorComponent],
         providers: [
            LoadingService,
            { provide: Router, useValue: { events: routerEvents$.asObservable() } },
         ],
      }).compileComponents();

      fixture = TestBed.createComponent(LoadingIndicatorComponent);
      component = fixture.componentInstance;
      loadingService = TestBed.inject(LoadingService);
   });

   it('should create', () => {
      fixture.detectChanges();
      expect(component).toBeTruthy();
   });

   it('should toggle loading service during route config load when enabled', () => {
      fixture.componentRef.setInput('detectRouteTransitions', true);
      const onSpy = vi.spyOn(loadingService, 'loadingOn');
      const offSpy = vi.spyOn(loadingService, 'loadingOff');

      fixture.detectChanges();

      routerEvents$.next(new RouteConfigLoadStart({ path: 'lazy' } as never));
      routerEvents$.next(new RouteConfigLoadEnd({ path: 'lazy' } as never));

      expect(onSpy).toHaveBeenCalled();
      expect(offSpy).toHaveBeenCalled();
   });
});
