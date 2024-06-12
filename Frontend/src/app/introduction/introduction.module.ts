import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IntroductionComponent } from './introduction.component';
import { INTRODUCTION_ROUTE } from './introduction.route';

@NgModule({
  imports: [ RouterModule.forChild([INTRODUCTION_ROUTE])],
  declarations: [IntroductionComponent],
})
export class IntroductionModule {}
