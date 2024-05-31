import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CART_ROUTE } from './cart.route';
import { SharedModule } from '../shared/shared.module';
import { CartComponent } from './cart.component';

@NgModule({
  imports: [SharedModule, RouterModule.forChild([CART_ROUTE])],
  declarations: [CartComponent],
})
export class CartModule {}

