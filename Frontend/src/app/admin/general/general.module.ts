import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'src/app/shared/shared.module';
import { GeneralComponent } from './general.component';
import { GENERAL_ROUTE } from './general.route';

@NgModule({
  imports: [ SharedModule,RouterModule.forChild([GENERAL_ROUTE])],
  declarations: [GeneralComponent],
})
export class GeneralModule {}
