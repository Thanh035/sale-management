import { Route } from '@angular/router';

import { UserRouteAccessService } from 'src/app/core/auth/user-route-access.service';
import { AddressesComponent } from './addresses.component';

export const addressesRoute: Route = {
  path: 'addresses',
  component: AddressesComponent,
  title: 'Sổ địa chỉ',
  canActivate: [UserRouteAccessService],
};
