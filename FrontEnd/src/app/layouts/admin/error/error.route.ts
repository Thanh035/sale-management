import { Routes } from '@angular/router';
import { ErrorOfAdminComponent } from './error.component';
import { PermissionComponent } from './permission/permission.component';


export const errorOfAdminRoute: Routes = [
  {
    path: 'accessdenied',
    component: PermissionComponent,
    data: {
      errorMessage: 'Bạn không được phân quyền để truy cập trang này.',
      statusError: '403'
    },
    title: 'Lỗi phân quyền'
  },
  {
    path: '404',
    component: ErrorOfAdminComponent,
    data: {
      errorMessage: 'Trang bạn đang tìm kiếm không tồn tại',
      statusError: '404'
    },
    title: 'Lỗi 404'
  },
  {
    path: '**',
    redirectTo: '404',
  },
];
