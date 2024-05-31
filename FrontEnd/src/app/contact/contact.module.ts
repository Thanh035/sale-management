import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CONTACT_ROUTE } from './contact.route';
import { SharedModule } from '../shared/shared.module';
import { ContactComponent } from './contact.component';

@NgModule({
  imports: [SharedModule, RouterModule.forChild([CONTACT_ROUTE])],
  declarations: [ContactComponent],
})
export class ContactModule {}

