import { Routes } from '@angular/router';
import { registerRoute } from './register/register.route';
import { activateRoute } from './activate/activate.route';
import { passwordRoute } from './password/password.route';
import { passwordResetInitRoute } from './password-reset/init/password-reset-init.route';
import { passwordResetFinishRoute } from './password-reset/finish/password-reset-finish.route';
import { settingsRoute } from './settings/settings.route';
import { loginRoute } from './login/login.route';
import { addressesRoute } from './addresses/addresses.route';
import { ordersRoute } from './orders/orders.coute';

const ACCOUNT_ROUTES = [activateRoute, passwordRoute, passwordResetFinishRoute, passwordResetInitRoute, registerRoute, settingsRoute,loginRoute,addressesRoute,ordersRoute];

export const accountState: Routes = [
  {
    path: '',
    children: ACCOUNT_ROUTES
  },
];
