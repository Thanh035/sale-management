import { Route } from '@angular/router';

import { SettingsComponent } from './settings.component';
import { UserRouteAccessService } from 'src/app/core/auth/user-route-access.service';

export const settingsRoute: Route = {
  path: '',
  component: SettingsComponent,
  title: 'Trang khách hàng',
  canActivate: [UserRouteAccessService],
};
