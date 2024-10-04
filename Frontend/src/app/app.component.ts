import { Component } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { faLeaf } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
})
export class AppComponent {
  isAdmin: boolean = false;

  isWeb: boolean = false;

  login: boolean = false;

  constructor(private router: Router) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {

        if(event.url.startsWith('/admin')) {
          this.isAdmin = true;
        } else if(event.url.startsWith('/login'))  {
          this.login = true;
        } else {
          this.isWeb = true;
        }
      }
    });
  }

}
