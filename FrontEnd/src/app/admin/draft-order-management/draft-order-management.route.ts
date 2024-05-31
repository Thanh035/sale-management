import { ActivatedRouteSnapshot, Resolve, Routes } from '@angular/router';
import { Observable, of } from 'rxjs';
import { Injectable } from '@angular/core';
import { DraftOrderManagementComponent } from './list/draft-order-management.component';
import { DraftOrderManagementUpdateComponent } from './update/draft-order-management-update.component';
import { DraftOrderViewDTO } from './draft-order-management.model';
import { DraftOrderManagementService } from './service/draft-order-management.service';

@Injectable({ providedIn: 'root' })
export class DraftOrderManagementResolve implements Resolve<DraftOrderViewDTO | null> {
  constructor(private service: DraftOrderManagementService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<DraftOrderViewDTO | null> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id);
    }
    return of(null);
  }
}

export const DraftOrderManagementRoute: Routes = [
  {
    path: '',
    component: DraftOrderManagementComponent,
    data: {
      defaultSort: 'id,asc',
    },
  },
  {
    path: ':id',
    component: DraftOrderManagementUpdateComponent,
    resolve: {
      draftOrder: DraftOrderManagementResolve
    },
  },
];
