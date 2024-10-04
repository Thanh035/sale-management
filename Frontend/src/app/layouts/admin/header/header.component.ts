import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { Account } from 'src/app/core/auth/account.model';
import { AccountService } from 'src/app/core/auth/account.service';
import { LoginService } from 'src/app/login/login.service';

@Component({
  selector: 'app-header-admin',
  templateUrl: './header.component.html',
})
export class HeaderOfAdminComponent implements OnInit {
  account: Account | null = null;
  profileImage: any;

  constructor(
    private loginService: LoginService,
    private accountService: AccountService,
    private router: Router,
    private sanitizer: DomSanitizer
  ) {
  }

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;

      if(account?.profileImageId) {
        this.accountService.profileImage()
        .subscribe((blob : any) => {
          let objectURL = URL.createObjectURL(blob);
          this.profileImage = this.sanitizer.bypassSecurityTrustUrl(objectURL);
        });
      }
    });
  }

  logout(): void {
    this.loginService.logout();
    // this.router.navigate(['/login']);
    window.location.href = '/login';
  }
}
