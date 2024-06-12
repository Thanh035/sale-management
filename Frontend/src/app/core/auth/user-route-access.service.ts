import { Injectable, isDevMode } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { AccountService } from 'src/app/core/auth/account.service';
import { StateStorageService } from './state-storage.service';

@Injectable({ providedIn: 'root' })
export class UserRouteAccessService implements CanActivate {
  constructor(private router: Router, private accountService: AccountService, private stateStorageService: StateStorageService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    return this.accountService.identity().pipe(
      map(account => {
        if (account) {
          const roles = route.data['roles'];

          if (!roles || roles.length === 0 || this.accountService.hasAnyRole(roles)) {
            return true;
          }

          this.router.navigate(['admin/accessdenied']);
          return false;
        }

        this.stateStorageService.storeUrl(state.url);
        // this.router.navigate(['/login']);
        window.location.href = "/login";
        return false;
      })
    );
  }
}
