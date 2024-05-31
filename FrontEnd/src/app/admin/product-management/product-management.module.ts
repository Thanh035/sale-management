import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { productManagementRoute } from './product-management.route';
import { ProductManagementUpdateComponent } from './update/product-management-update.component';
import { ProductManagementComponent } from './list/product-management.component';
import { ProductManagementDeleteDialogComponent } from './delete/product-management-delete-dialog.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { ProductManagementCreateComponent } from './create/product-management-create.component';

@NgModule({
  imports: [SharedModule,RouterModule.forChild(productManagementRoute)],
  declarations: [
    ProductManagementComponent,
    ProductManagementUpdateComponent,
    ProductManagementDeleteDialogComponent,
    ProductManagementCreateComponent,
  ]
})
export class ProductManagementModule {}
