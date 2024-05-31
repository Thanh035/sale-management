import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SettingsComponent } from './settings.component';
import { SETTINGS_ROUTE } from './settings.route';



@NgModule({
  imports: [RouterModule.forChild([SETTINGS_ROUTE])],
  declarations: [SettingsComponent],
})
export class SettingsModule {}

