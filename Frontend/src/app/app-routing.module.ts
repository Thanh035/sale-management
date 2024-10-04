import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { UserRouteAccessService } from './core/auth/user-route-access.service';
import { Role } from './config/role.constants';


@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        {
          path: 'admin',
          // data: {
          //   roles: [Role.ADMIN,Role.MANAGER,Role.STAFF],
          // },
          canActivate: [UserRouteAccessService],
          loadChildren: () => import('./admin/admin-routing.module').then(m => m.AdminRoutingModule),
        },
        {
          path: 'account',
          loadChildren: () => import('./account/account.module').then(m => m.AccountModule),
        },
        {
          path: 'login',
          loadChildren: () => import('./login/login.module').then(m => m.LoginModule),
        },
        {
          path: 'home',
          loadChildren: () => import('./home/home.module').then(m => m.HomeModule),
        },
        {
          path: 'introduction',
          loadChildren: () => import('./introduction/introduction.module').then(m => m.IntroductionModule),
        },
        {
          path: 'collections',
          loadChildren: () => import('./collections/collections.module').then(m => m.CollectionsModule),
        },
        {
          path: 'news',
          loadChildren: () => import('./news/news.module').then(m => m.NewsModule),
        },
        {
          path: 'cart',
          loadChildren: () => import('./cart/cart.module').then(m => m.CartModule),
        },
        {
          path: 'contact',
          loadChildren: () => import('./contact/contact.module').then(m => m.ContactModule),
        },
        {
          path: '',
          redirectTo: '/home',
          pathMatch: 'full'
        },
        // ...errorRoute
      ]
    ),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
