import { routes } from './app.routes';

describe('app routes', () => {
   it('should contain expected paths and wildcard redirect', () => {
      const paths = routes.map((route) => route.path);

      expect(paths).toContain('login');
      expect(paths).toContain('home');
      expect(paths).toContain('admin');
      expect(paths).toContain('pauta/:type');
      expect(paths).toContain('associado/:type');
      expect(paths).toContain('**');

      const wildcard = routes.find((route) => route.path === '**');
      expect(wildcard?.redirectTo).toBe('login');
   });

   it('should protect private routes with canActivate', () => {
      const protectedPaths = ['home', 'admin', 'pauta/:type', 'associado/:type'];

      protectedPaths.forEach((path) => {
         const route = routes.find((item) => item.path === path);
         expect(route).toBeTruthy();
         expect(route?.canActivate?.length).toBeGreaterThan(0);
      });
   });

   it('should expose titles for all main routes', () => {
      const routesWithTitle = routes.filter((route) => route.path !== '**');

      routesWithTitle.forEach((route) => {
         expect(route.data?.['title']).toBeTruthy();
      });
   });

   it('should resolve lazy components from route definitions', async () => {
      const lazyRoutes = routes.filter(
         (route) => route.path !== '**' && !!route.loadComponent,
      );

      for (const route of lazyRoutes) {
         const component = await route.loadComponent?.();
         expect(component).toBeTruthy();
      }
   });
});
