import { inject, Injectable } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { filter, map } from 'rxjs/operators';

@Injectable({
   providedIn: 'root',
})
export class TitleService {
   private title = inject(Title);
   private router = inject(Router);
   private activatedRoute = inject(ActivatedRoute);

   setTitle() {
      this.router.events
         .pipe(
            filter((event) => event instanceof NavigationEnd),
            map(() => {
               let route = this.activatedRoute;
               while (route.firstChild) {
                  route = route.firstChild;
               }
               return route;
            }),
            filter((route) => route.outlet === 'primary'),
            map((route) => {
               const title = route.snapshot.data['title'];
               if (title) {
                  return title;
               }
               return 'My App Title';
            }),
         )
         .subscribe((title) => this.title.setTitle(title));
   }
}
