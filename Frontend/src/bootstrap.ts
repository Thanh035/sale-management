import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { AppModule } from 'src/app/app.module';


platformBrowserDynamic()
  .bootstrapModule(AppModule)
  .then(() => console.log('Application started'))
  .catch(err => console.error(err));
