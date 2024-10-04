import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SharedModule } from 'src/app/shared/shared.module';
import { OrderManagementRoute } from './order-management.route';
import { OrderManagementComponent } from './list/order-management.component';
import { OrderManagementCreateComponent } from './create/order-management-create.component';
import { OrderManagementUpdateComponent } from './update/order-management-update.component';
import { OrderManagementDeleteDialogComponent } from './delete/order-management-delete-dialog.component';
import { OrderManagementSearchDialogComponent } from './search/order-management-search-dialog.component';
import { OrderManagementCheckoutDialogComponent } from './checkout/order-management-checkout-dialog.component';
import { CustomerCreateDialogComponent } from './customer/customer-create-dialog.component';
import { PaymentDialogComponent } from './payment/payment-dialog.component';


@NgModule({
  imports: [SharedModule,RouterModule.forChild(OrderManagementRoute)],
  declarations: [
    OrderManagementComponent,
    OrderManagementCreateComponent,
    OrderManagementUpdateComponent,

    OrderManagementDeleteDialogComponent,
    OrderManagementSearchDialogComponent,
    OrderManagementCheckoutDialogComponent,

    CustomerCreateDialogComponent,
    PaymentDialogComponent
  ]
})
export class OrderManagementModule {}
