import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DashboardComponent } from './dashboard.component';
import { DASHBOARD_ROUTE } from './dashboard.route';

@NgModule({
  imports: [ RouterModule.forChild([DASHBOARD_ROUTE])],
  declarations: [DashboardComponent],
})
export class DashboardModule {}
