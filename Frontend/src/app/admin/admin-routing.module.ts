import { NgModule, Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Role } from '../config/role.constants';
import { UserRouteAccessService } from '../core/auth/user-route-access.service';
import { errorOfAdminRoute } from '../layouts/admin/error/error.route';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'users',
        loadChildren: () => import('./user-management/user-management.module').then(m => m.UserManagementModule),
        title: 'Danh sách người dùng',
        data: {
          roles: [Role.ADMIN],
        },
        canActivate: [UserRouteAccessService],
      },
      {
        path: 'role_permissions',
        loadChildren: () => import('./role-permissions/role-permission.module').then(m => m.RolePermissionModule),
        title: 'Danh sách vai trò',
        data: {
          roles: [Role.ADMIN],
        },
        canActivate: [UserRouteAccessService],
      },
      {
        path: 'products',
        loadChildren: () => import('./product-management/product-management.module').then(m => m.ProductManagementModule),
        title: 'Danh sách sản phẩm',
        canActivate: [UserRouteAccessService],
      },
      {
        path: 'orders',
        loadChildren: () => import('./order-management/order-management.module').then(m => m.OrderManagementModule),
        title: 'Danh sách đơn hàng',
        data: {
          roles: [Role.ADMIN,Role.MANAGER,Role.STAFF],
        },
        canActivate: [UserRouteAccessService],
      },
      {
        path: 'draft_orders',
        loadChildren: () => import('./draft-order-management/draft-order-management.module').then(m => m.DraftOrderManagementModule),
        title: 'Đơn hàng nháp',
        data: {
          roles: [Role.ADMIN,Role.MANAGER,Role.STAFF],
        },
        canActivate: [UserRouteAccessService],
      },
      {
        path: 'customers',
        loadChildren: () => import('./customer-management/customer-management.module').then(m => m.CustomerManagementModule),
        title: 'Danh sách khách hàng',
        data: {
          roles: [Role.ADMIN,Role.MANAGER,Role.STAFF],
        },
        canActivate: [UserRouteAccessService]
      },
      {
        path: 'collections',
        loadChildren: () => import('./collection-management/collection-management.module').then(m => m.CollectionManagementModule),
        title: 'Danh sách nhóm sản phẩm',
        data: {
          roles: [Role.ADMIN,Role.MANAGER,Role.STAFF],
        },
        canActivate: [UserRouteAccessService]
      },
        {
        path: 'profile',
        loadChildren: () => import('./profile/profile.module').then(m => m.ProfileModule),
        title: 'Hồ sơ cá nhân',
      },
        {
        path: 'security',
        loadChildren: () => import('./security/security.module').then(m => m.SecurityModule),
        title: 'Bảo mật',
      },
        {
        path: 'general',
        loadChildren: () => import('./general/general.module').then(m => m.GeneralModule),
        title: 'Cấu hình chung',
      },
      {
        path: 'settings',
        loadChildren: () => import('./settings/settings.module').then(m => m.SettingsModule),
        title: 'Cấu hình chung',
        data: {
          roles: [Role.ADMIN],
        },
        canActivate: [UserRouteAccessService],
      },
      {
        path: 'dashboard',
        loadChildren: () => import('./dashboard/dashboard.module').then(m => m.DashboardModule),
        title: 'Tổng quan',
        data: {
          roles: [Role.ADMIN,Role.MANAGER,Role.STAFF],
        },
        canActivate: [UserRouteAccessService]
      },
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      },
      ...errorOfAdminRoute
    ]),
  ],
})
export class AdminRoutingModule {}
