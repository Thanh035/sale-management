import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SecurityComponent } from './security.component';
import { SECURITY_ROUTE } from './security.route';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  imports: [SharedModule,RouterModule.forChild(SECURITY_ROUTE)],
  declarations: [SecurityComponent,ChangePasswordComponent],
})
export class SecurityModule {}
