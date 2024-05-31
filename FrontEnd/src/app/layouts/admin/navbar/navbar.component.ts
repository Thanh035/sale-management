import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Account } from 'src/app/core/auth/account.model';
import { AccountService } from 'src/app/core/auth/account.service';
import { LoginService } from 'src/app/login/login.service';

@Component({
  selector: 'app-navbar-admin',
  templateUrl: './navbar.component.html',
})
export class NavbarOfAdminComponent implements OnInit {
  account: Account | null = null;
  productList: boolean = false;
  saleList: boolean = false;
  userList: boolean = false;
  quotationList: boolean = false;
  reportList: boolean = false;
  expenseList : boolean = false;

  toggleProductList() {
    this.productList = !this.productList;
  }

  toggleSaleList() {
    this.saleList = !this.saleList;
  }

  toggleUserList() {
    this.userList = !this.userList;
  }

  toggleReportList() {
    this.reportList = !this.reportList;
  }

  toggleQuotationList() {
    this.quotationList = !this.quotationList;
  }

  toggleExpenseList() {
    this.expenseList = !this.expenseList;
  }

  constructor(
    private loginService: LoginService,
    private accountService: AccountService,
    private router: Router
  ) {
  }

  roles?: string[];

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
      this.roles = account?.roles;
    });
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  logout(): void {
    this.loginService.logout();
    this.router.navigate(['admin']);
  }
}
