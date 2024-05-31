import { ActivatedRouteSnapshot, Resolve, Routes } from '@angular/router';
import { Observable, of } from 'rxjs';
import { Injectable } from '@angular/core';
import { CustomerDetailDTO } from './customer-management.model';
import { CustomerManagementComponent } from './list/customer-management.component';
import { CustomerManagementUpdateComponent } from './update/customer-management-update.component';
import { CustomerManagementService } from './service/customer-management.service';
import { CustomerManagementCreateComponent } from './create/customer-management-create.component';

@Injectable({ providedIn: 'root' })
export class CustomerManagementResolve implements Resolve<CustomerDetailDTO | null> {
  constructor(private service: CustomerManagementService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<CustomerDetailDTO | null> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id);
    }
    return of(null);
  }
}

export const CustomerManagementRoute: Routes = [
  {
    path: '',
    component: CustomerManagementComponent,
    data: {
      defaultSort: 'id,asc',
    },
  },
  {
    path: 'new',
    component: CustomerManagementCreateComponent,
  },
  {
    path: ':id',
    component: CustomerManagementUpdateComponent,
    resolve: {
      customer: CustomerManagementResolve
    },
  },
  // {
  //   path: ':id',
  //   component: CustomerContactDialogComponent,
  //   resolve: {
  //     customer: CustomerManagementResolve
  //   },
  // }
];
