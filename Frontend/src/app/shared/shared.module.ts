import { NgModule } from '@angular/core';

import { SharedLibsModule } from './shared-libs.module';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { ItemCountComponent } from './pagination/item-count.component';
import { SortDirective } from './sort/sort.directive';
import { SortByDirective } from './sort/sort-by.directive';
import { FilterComponent } from './filter/filter.component';

@NgModule({
  imports: [SharedLibsModule],
  declarations: [
    AlertComponent,
    AlertErrorComponent,
    ItemCountComponent,
    SortDirective,
    SortByDirective,
    FilterComponent
  ],
  exports: [
    SharedLibsModule,
    AlertComponent,
    AlertErrorComponent,
    ItemCountComponent,
    SortDirective,
    SortByDirective,
    FilterComponent
  ],
})
export class SharedModule {}
