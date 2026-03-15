import { TestBed } from '@angular/core/testing';

import { PermissionStore } from './permission-store';

describe('PermissionService', () => {
   let service: PermissionStore;

   beforeEach(() => {
      TestBed.configureTestingModule({
         providers: [PermissionStore],
      });
      service = TestBed.inject(PermissionStore);
   });

   it('should be created', () => {
      expect(service).toBeTruthy();
   });
});
