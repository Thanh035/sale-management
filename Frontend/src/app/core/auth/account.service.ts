import { Injectable } from '@angular/core';
import { Account, SecurityInfoDTO, UserUpdateRequest } from './account.model';
import { Observable, ReplaySubject, catchError, of, shareReplay, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { StateStorageService } from './state-storage.service';
import { Router } from '@angular/router';
import { ApplicationConfigService } from '../config/application-config.service';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private userIdentity: Account | null = null;
  private authenticationState = new ReplaySubject<Account | null>(1);
  private accountCache$?: Observable<Account> | null;

  constructor(
    private http: HttpClient,
    private stateStorageService: StateStorageService,
    private router: Router,
    private applicationConfigService: ApplicationConfigService
  ) {}

  save(account: UserUpdateRequest): Observable<{}> {
    return this.http.put(this.applicationConfigService.getEndpointFor('api/v1.0/account'), account);
  }

  authenticate(identity: Account | null): void {
    this.userIdentity = identity;
    this.authenticationState.next(this.userIdentity);
    if (!identity) {
      this.accountCache$ = null;
    }
  }

  hasAnyRole(roles: string[] | string): boolean {
    if (!this.userIdentity) {
      return false;
    }
    if (!Array.isArray(roles)) {
      roles = [roles];
    }
    return this.userIdentity.roles.some((Role: string) => roles.includes(Role));
  }

  identity(force?: boolean): Observable<Account | null> {
    if (!this.accountCache$ || force) {
      this.accountCache$ = this.fetch().pipe(
        tap((account: Account) => {
          this.authenticate(account);
          this.navigateToStoredUrl();
        }),
        shareReplay()
      );
    }
    return this.accountCache$.pipe(catchError(() => of(null)));
  }

  isAuthenticated(): boolean {
    return this.userIdentity !== null;
  }

  getAuthenticationState(): Observable<Account | null> {
    return this.authenticationState.asObservable();
  }

  private fetch(): Observable<Account> {
    return this.http.get<Account>(this.applicationConfigService.getEndpointFor('api/v1.0/account'));
  }

  private navigateToStoredUrl(): void {
    // previousState can be set in the authExpiredInterceptor and in the userRouteAccessService
    // if login is successful, go to stored previousState and clear previousState
    const previousUrl = this.stateStorageService.getUrl();
    if (previousUrl) {
      this.stateStorageService.clearUrl();
      this.router.navigateByUrl(previousUrl);
    }
  }

  profileImage() {
    return this.http.get(this.applicationConfigService.getEndpointFor('api/v1.0/account/profile-image'),{ responseType: 'blob' });
  }

  securityInfo(): Observable<SecurityInfoDTO | null> {
    return this.http.get<SecurityInfoDTO>(this.applicationConfigService.getEndpointFor('api/v1.0/securityInfo'));
  }

}
