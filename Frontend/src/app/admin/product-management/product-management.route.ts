import { ActivatedRouteSnapshot, Resolve, Routes } from '@angular/router';
import { ProductManagementComponent } from './list/product-management.component';
import { ProductManagementUpdateComponent } from './update/product-management-update.component';
import { ProductDetailDTO } from './product-management.model';
import { ProductManagementService } from './service/product-management.service';
import { Observable, of } from 'rxjs';
import { Injectable } from '@angular/core';
import { ProductManagementCreateComponent } from './create/product-management-create.component';

@Injectable({ providedIn: 'root' })
export class ProductManagementResolve implements Resolve<ProductDetailDTO | null> {
  constructor(private service: ProductManagementService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ProductDetailDTO | null> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id);
    }
    return of(null);
  }
}

export const productManagementRoute: Routes = [
  {
    path: '',
    component: ProductManagementComponent,
    data: {
      defaultSort: 'id,asc',
    },
  },
  {
    path: 'new',
    component: ProductManagementCreateComponent
  },
  {
    path: ':id',
    component: ProductManagementUpdateComponent,
    resolve: {
      product: ProductManagementResolve,
    },
  },
];
