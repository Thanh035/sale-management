import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProfileComponent } from './profile.component';
import { PROFILE_ROUTE } from './profile.route';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  imports: [ SharedModule,RouterModule.forChild([PROFILE_ROUTE])],
  declarations: [ProfileComponent],
})
export class ProfileModule {}
