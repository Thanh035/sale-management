import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SharedModule } from 'src/app/shared/shared.module';
import { DraftOrderManagementRoute } from './draft-order-management.route';
import { DraftOrderManagementComponent } from './list/draft-order-management.component';
import { DraftOrderManagementDeleteDialogComponent } from './delete/draft-order-management-delete-dialog.component';
import { DraftOrderManagementUpdateComponent } from './update/draft-order-management-update.component';


@NgModule({
  imports: [SharedModule,RouterModule.forChild(DraftOrderManagementRoute)],
  declarations: [
    DraftOrderManagementComponent,
    DraftOrderManagementDeleteDialogComponent,
    DraftOrderManagementUpdateComponent
  ]
})
export class DraftOrderManagementModule {}
