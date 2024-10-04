import { ActivatedRouteSnapshot, Resolve, Routes } from '@angular/router';
import { Observable, of } from 'rxjs';
import { Injectable } from '@angular/core';
import { OrderDetailDTO } from './order-management.model';
import { OrderManagementService } from './service/order-management.service';
import { OrderManagementUpdateComponent } from './update/order-management-update.component';
import { OrderManagementComponent } from './list/order-management.component';
import { OrderManagementCreateComponent } from './create/order-management-create.component';

@Injectable({ providedIn: 'root' })
export class OrderManagementResolve implements Resolve<OrderDetailDTO | null> {
  constructor(private service: OrderManagementService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<OrderDetailDTO | null> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id);
    }
    return of(null);
  }
}

export const OrderManagementRoute: Routes = [
  {
    path: '',
    component: OrderManagementComponent,
    data: {
      defaultSort: 'id,asc',
    },
  },
  {
    path: 'new',
    component: OrderManagementCreateComponent,
  },
  {
    path: ':id',
    component: OrderManagementUpdateComponent,
    resolve: {
      order: OrderManagementResolve
    },
  },

];
