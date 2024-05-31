import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SharedModule } from 'src/app/shared/shared.module';
import { CollectionManagementRoute } from './collection-management.route';
import { CollectionManagementCreateComponent } from './create/collection-management-create.component';
import { CollectionManagementComponent } from './list/collection-management.component';
import { CollectionManagementUpdateComponent } from './update/collection-management-update.component';
import { CollectionManagementSearchDialogComponent } from './search/collection-management-search-dialog.component';
import { CollectionManagementDeleteDialogComponent } from './delete/collection-management-delete-dialog.component';


@NgModule({
  imports: [SharedModule,RouterModule.forChild(CollectionManagementRoute)],
  declarations: [
    CollectionManagementComponent,
    CollectionManagementUpdateComponent,
    CollectionManagementCreateComponent,
    CollectionManagementDeleteDialogComponent,

    CollectionManagementSearchDialogComponent
  ]
})
export class CollectionManagementModule {}
