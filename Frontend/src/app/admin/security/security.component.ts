import { Component, OnInit } from '@angular/core';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AccountService } from 'src/app/core/auth/account.service';
import { SecurityInfoDTO } from 'src/app/core/auth/account.model';
import * as UAParser from 'ua-parser-js';

@Component({
  selector: 'app-security',
  templateUrl: './security.component.html',
})
export class SecurityComponent implements OnInit {

  constructor(
    private modalService: NgbModal,
    private accountService: AccountService
  ) {}

  securityInfo: SecurityInfoDTO | null = null;

  ngOnInit(): void {
    this.accountService.securityInfo().subscribe(securityInfo => {
      if (securityInfo) {
        this.securityInfo = securityInfo;
      }
    });
  }

  public displayBrowser(browserInfo: string) : string {
    const parser = new UAParser();
    const result = parser.setUA(browserInfo).getResult();
    return `${result.browser.name} ${result.browser.version} on ${result.os.name} ${result.os.version}`;
  }

  changePassword(): void {
    const modalRef = this.modalService.open(
      ChangePasswordComponent,
      { size: 'lg', backdrop: 'static' }
    );

    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe((reason) => {
      if (reason === 'deleted') {
      }
    });
  }

}
