import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { userManagementRoute } from './user-management.route';
import { UserManagementDeleteDialogComponent } from './delete/user-management-delete-dialog.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { UserManagementComponent } from './list/user-management.component';
import { UserManagementUpdateComponent } from './update/user-management-update.component';
import { UserManagementCreateDialogComponent } from './create/user-management-create-dialog.component';

@NgModule({
  imports: [SharedModule,RouterModule.forChild(userManagementRoute)],
  declarations: [
    UserManagementComponent,
    UserManagementUpdateComponent,
    UserManagementDeleteDialogComponent,
    UserManagementCreateDialogComponent
  ]
})
export class UserManagementModule {}
