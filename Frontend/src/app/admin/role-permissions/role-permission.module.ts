import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SharedModule } from 'src/app/shared/shared.module';
import { RolePermissionRoute } from './role-permission.route';
import { RolePermissionsComponent } from './list/role-permissions.component';
import { RolePermissionsUpdateComponent } from './update/role-permissions-update.component';
import { RolePermissionsCreateComponent } from './create/role-permissions-create.component';
import { RolePermissionsDeleteComponent } from './delete/role-permissions-delete.component';

@NgModule({
  imports: [SharedModule,RouterModule.forChild(RolePermissionRoute)],
  declarations: [
    RolePermissionsComponent,
    // RolePermissionsUpdateComponent,
    // RolePermissionsCreateComponent,
    // RolePermissionsDeleteComponent
  ]
})
export class RolePermissionModule {}
