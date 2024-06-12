import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LOGIN_ROUTE } from './login.route';
import { LoginComponent } from './login.component';
import { SharedModule } from '../shared/shared.module';
import { ForgotPasswordFinishComponent } from './forgot-password/finish/password-reset-finish.component';
import { ForgotPasswordInitComponent } from './forgot-password/init/password-reset-init.component';
import { FORGOT_PASSWORD_FINISH_ROUTE } from './forgot-password/finish/password-reset-finish.route';
import { FORGOT_PASSWORD_INIT_ROUTE } from './forgot-password/init/password-reset-init.route';

@NgModule({
  imports: [SharedModule, RouterModule.forChild([LOGIN_ROUTE,FORGOT_PASSWORD_FINISH_ROUTE,FORGOT_PASSWORD_INIT_ROUTE])],
  declarations: [LoginComponent,ForgotPasswordFinishComponent,ForgotPasswordInitComponent],
})
export class LoginModule {}

