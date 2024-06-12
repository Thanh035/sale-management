import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { NavbarOfAdminComponent } from './layouts/admin/navbar/navbar.component';
import { ErrorComponent } from './layouts/web/error/error.component';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { HeaderOfAdminComponent } from './layouts/admin/header/header.component';
import { FooterOfWebComponent } from './layouts/web/footer/footer.component';
import { HeaderOfWebComponent } from './layouts/web/header/header.component';
import { NavbarOfWebComponent } from './layouts/web/navbar/navbar.component';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { httpInterceptorProviders } from './core/interceptor';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { fontAwesomeIcons } from './core/config/font-awesome-icons';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule(
  {
  declarations: [
    HeaderOfAdminComponent,
    NavbarOfAdminComponent,

    HeaderOfWebComponent,
    FooterOfWebComponent,
    NavbarOfWebComponent,

    AppComponent,
    ErrorComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    NgxWebstorageModule.forRoot({ prefix: 'app', separator: '-', caseSensitive: true }),
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent],
  }
)
export class AppModule {
  constructor(iconLibrary:FaIconLibrary) {
    iconLibrary.addIcons(...fontAwesomeIcons);
  }

}
