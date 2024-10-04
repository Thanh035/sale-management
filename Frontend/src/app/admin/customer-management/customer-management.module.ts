import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SharedModule } from 'src/app/shared/shared.module';
import { CustomerManagementComponent } from './list/customer-management.component';
import { CustomerManagementUpdateComponent } from './update/customer-management-update.component';
import { CustomerManagementCreateComponent } from './create/customer-management-create.component';
import { CustomerManagementDeleteDialogComponent } from './delete/customer-management-delete-dialog.component';
import { CustomerManagementRoute } from './customer-management.route';
import { CustomerContactDialogComponent } from './contact/customer-contact-dialog.component';
import { CustomerAddressDialogComponent } from './address/customer-address-dialog.component';
import { CustomerAddressInfoDialogComponent } from './address-info/customer-address-info-dialog.component';


@NgModule({
  imports: [SharedModule,RouterModule.forChild(CustomerManagementRoute)],
  declarations: [
    CustomerManagementComponent,
    CustomerManagementUpdateComponent,
    CustomerManagementCreateComponent,
    CustomerManagementDeleteDialogComponent,

    CustomerContactDialogComponent,
    CustomerAddressDialogComponent,
    CustomerAddressInfoDialogComponent
  ]
})
export class CustomerManagementModule {}
