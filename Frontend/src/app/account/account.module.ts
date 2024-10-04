import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { accountState } from './account.route';
import { SharedModule } from '../shared/shared.module';
import { RegisterComponent } from './register/register.component';
import { PasswordComponent } from './password/password.component';
import { ActivateComponent } from './activate/activate.component';
import { SettingsComponent } from './settings/settings.component';
import { PasswordStrengthBarComponent } from './password/password-strength-bar/password-strength-bar.component';
import { PasswordResetInitComponent } from './password-reset/init/password-reset-init.component';
import { PasswordResetFinishComponent } from './password-reset/finish/password-reset-finish.component';
import { LoginComponent } from './login/login.component';
import { AddressesComponent } from './addresses/addresses.component';
import { OrdersComponent } from './orders/orders.component';

@NgModule({
  imports: [SharedModule, RouterModule.forChild(accountState)],
  declarations: [
    RegisterComponent,
    PasswordComponent,
    ActivateComponent,
    SettingsComponent,
    PasswordStrengthBarComponent,
    PasswordResetInitComponent,
    PasswordResetFinishComponent,
    LoginComponent,
    AddressesComponent,
    OrdersComponent,
  ],
})
export class AccountModule {}
