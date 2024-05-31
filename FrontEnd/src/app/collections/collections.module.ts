import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CollectionsComponent } from './collections.component';
import { COLLECTIONS_ROUTE } from './collections.route';

@NgModule({
  imports: [ RouterModule.forChild([COLLECTIONS_ROUTE])],
  declarations: [CollectionsComponent],
})
export class CollectionsModule {}
