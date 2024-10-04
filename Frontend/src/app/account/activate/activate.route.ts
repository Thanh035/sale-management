import { Route } from '@angular/router';

import { ActivateComponent } from './activate.component';

export const activateRoute: Route = {
  path: 'activate',
  component: ActivateComponent,
  // data: {
  //   pageTitle: 'activate.title',
  // },
  title: "Kích hoạt tài khoản"
};
