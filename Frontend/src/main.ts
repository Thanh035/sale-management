/// <reference types="@angular/localize" />

import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';



platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));


import('./bootstrap').catch(err => console.error(err));

