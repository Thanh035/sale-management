import { Route } from '@angular/router';
import { ForgotPasswordInitComponent } from './password-reset-init.component';


export const FORGOT_PASSWORD_INIT_ROUTE: Route = {
  path: 'forgot_password/request',
  component: ForgotPasswordInitComponent,
  title:'Quên mật khẩu'
};
