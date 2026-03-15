import { Injectable } from '@angular/core';

@Injectable()
export class UserToken {
   id = 0;
   username = '';
   roles: string[] = [];
   permissions: string[] = [];
}
