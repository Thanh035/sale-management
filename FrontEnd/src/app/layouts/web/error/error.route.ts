import { Routes } from '@angular/router';

import { ErrorComponent } from './error.component';

export const errorRoute: Routes = [
  {
    path: 'error',
    component: ErrorComponent,
    data: {
      pageTitle: 'error.title',
    },
  },
  {
    path: 'accessdenied',
    component: ErrorComponent,
    data: {
      pageTitle: 'error.title',
      errorMessage: 'Forbidden',
      statusError: '403'
    },
  },
  {
    path: '404',
    component: ErrorComponent,
    data: {
      pageTitle: 'error.title',
      errorMessage: 'Not found',
      statusError: '404'
    },
  },
  {
    path: '**',
    redirectTo: '/404',
  }

];
