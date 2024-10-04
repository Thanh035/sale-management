import { ActivatedRouteSnapshot, Resolve, Routes } from '@angular/router';
import { Observable, of } from 'rxjs';
import { Injectable } from '@angular/core';
import { CollectionDetailDTO } from './collection-management.model';
import { CollectionManagementService } from './service/collection-management.service';
import { CollectionManagementCreateComponent } from './create/collection-management-create.component';
import { CollectionManagementComponent } from './list/collection-management.component';
import { CollectionManagementUpdateComponent } from './update/collection-management-update.component';

@Injectable({ providedIn: 'root' })
export class CollectionManagementResolve implements Resolve<CollectionDetailDTO | null> {
  constructor(private service: CollectionManagementService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<CollectionDetailDTO | null> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id);
    }
    return of(null);
  }
}

export const CollectionManagementRoute: Routes = [
  {
    path: '',
    component: CollectionManagementComponent,
    data: {
      defaultSort: 'id,asc',
    },
  },
  {
    path: 'new',
    component: CollectionManagementCreateComponent,
  },
  {
    path: ':id',
    component: CollectionManagementUpdateComponent,
    resolve: {
      collection: CollectionManagementResolve
    },
  },
];
