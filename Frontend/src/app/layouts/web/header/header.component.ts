import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Account } from 'src/app/core/auth/account.model';
import { AccountService } from 'src/app/core/auth/account.service';
import { LoginService } from 'src/app/login/login.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
})
export class HeaderOfWebComponent implements OnInit {

  account: Account | null = null;

  constructor(
    private loginService: LoginService,
    // private sessionStorageService: SessionStorageService,
    private accountService: AccountService,
    // private profileService: ProfileService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });
  }

  logout(): void {
    this.loginService.logout();
    this.router.navigate(['/home']);
  }

  login(): void {
    this.router.navigate(['/account/login']);
  }

}
