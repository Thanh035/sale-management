import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Account } from 'src/app/core/auth/account.model';
import { AccountService } from 'src/app/core/auth/account.service';

const initialAccount: Account = {} as Account;

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
})
export class SettingsComponent implements OnInit {

  account!: Account;

  constructor(private accountService: AccountService) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.account = account;
      }
    });
  }

}
