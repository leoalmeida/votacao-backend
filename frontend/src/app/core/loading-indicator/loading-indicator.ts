import {
   Component,
   inject,
   OnInit,
   TemplateRef,
   input,
   contentChild,
} from '@angular/core';
import { Observable, tap } from 'rxjs';
import { LoadingService } from '../loading.service';
import {
   RouteConfigLoadEnd,
   RouteConfigLoadStart,
   Router,
} from '@angular/router';
import { NgTemplateOutlet, AsyncPipe } from '@angular/common';
import { MatProgressSpinner } from '@angular/material/progress-spinner';

@Component({
   selector: 'app-loading-indicator',
   templateUrl: './loading-indicator.html',
   styleUrls: ['./loading-indicator.css'],
   imports: [NgTemplateOutlet, MatProgressSpinner, AsyncPipe],
})
export class LoadingIndicatorComponent implements OnInit {
   readonly detectRouteTransitions = input(false);
   loading$: Observable<boolean>;
   private loadingService: LoadingService = inject(LoadingService);
   private router: Router = inject(Router);

   readonly customLoadingIndicator = contentChild<TemplateRef<unknown> | null>(
      'loading',
   );

   constructor() {
      this.loading$ = this.loadingService.loading$;
   }

   ngOnInit() {
      if (this.detectRouteTransitions()) {
         this.router.events
            .pipe(
               tap((event) => {
                  if (event instanceof RouteConfigLoadStart) {
                     this.loadingService.loadingOn();
                  } else if (event instanceof RouteConfigLoadEnd) {
                     this.loadingService.loadingOff();
                  }
               }),
            )
            .subscribe();
      }
   }
}
