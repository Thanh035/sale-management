import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NEWS_ROUTE } from './news.route';
import { NewsComponent } from './news.component';

@NgModule({
  imports: [ RouterModule.forChild([NEWS_ROUTE])],
  declarations: [NewsComponent],
})
export class NewsModule {}
