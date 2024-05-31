import { Route } from '@angular/router';

import { UserRouteAccessService } from 'src/app/core/auth/user-route-access.service';
import { OrdersComponent } from './orders.component';

export const ordersRoute: Route = {
  path: 'orders',
  component: OrdersComponent,
  title: 'Chi tiết đơn hàng',
  canActivate: [UserRouteAccessService],
};
