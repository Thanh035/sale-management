import { Route } from '@angular/router';

import { ForgotPasswordFinishComponent } from './password-reset-finish.component';

export const FORGOT_PASSWORD_FINISH_ROUTE: Route = {
  path: 'forgot_password/finish',
  component: ForgotPasswordFinishComponent,
  // data: {
  //   pageTitle: 'global.menu.account.password',
  // },
  title:'Quên mật khẩu'

};
