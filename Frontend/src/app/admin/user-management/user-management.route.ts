import { ActivatedRouteSnapshot, Resolve, Routes } from '@angular/router';

import { UserManagementComponent } from './list/user-management.component';
import { UserManagementService } from './service/user-management.service';
import { Observable, of } from 'rxjs';
import { Injectable } from '@angular/core';
import { IUser, UserDetailDTO } from './user-management.model';
import { UserManagementUpdateComponent } from './update/user-management-update.component';

@Injectable({ providedIn: 'root' })
export class UserManagementResolve implements Resolve<UserDetailDTO | null> {
  constructor(private service: UserManagementService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<UserDetailDTO | null> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id);
    }
    return of(null);
  }
}

export const userManagementRoute: Routes = [
  {
    path: '',
    component: UserManagementComponent,
    data: {
      defaultSort: 'id,asc',
    },
  },
  {
    path: ':id',
    component: UserManagementUpdateComponent,
    resolve: {
      user: UserManagementResolve,
    },
  },
];
