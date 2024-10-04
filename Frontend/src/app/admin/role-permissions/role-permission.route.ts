import { ActivatedRouteSnapshot, Resolve, Routes } from '@angular/router';
import { Observable, of } from 'rxjs';
import { Injectable } from '@angular/core';
import { RolesService } from './service/roles.service';
import { RoleDetailDTO } from './role-permission.model';
import { RolePermissionsComponent } from './list/role-permissions.component';
import { RolePermissionsCreateComponent } from './create/role-permissions-create.component';
import { RolePermissionsUpdateComponent } from './update/role-permissions-update.component';

@Injectable({ providedIn: 'root' })
export class RolePermissionResolve implements Resolve<RoleDetailDTO | null> {
  constructor(private service: RolesService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<RoleDetailDTO | null> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id);
    }
    return of(null);
  }
}

export const RolePermissionRoute: Routes = [
  {
    path: '',
    component: RolePermissionsComponent
  },
  // {
  //   path: 'new',
  //   component: RolePermissionsCreateComponent
  // },
  // {
  //   path: ':id',
  //   component: RolePermissionsUpdateComponent,
  //   resolve: {
  //     product: RolePermissionResolve,
  //   },
  // },
];
