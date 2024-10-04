import { Route } from '@angular/router';

import { NavbarOfAdminComponent } from './navbar.component';

export const navbarRoute: Route = {
  path: '',
  component: NavbarOfAdminComponent,
  outlet: 'navbar',
};
