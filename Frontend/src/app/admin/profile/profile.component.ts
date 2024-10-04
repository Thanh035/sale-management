import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { Account, UserUpdateRequest } from 'src/app/core/auth/account.model';
import { AccountService } from 'src/app/core/auth/account.service';

const initialAccount: UserUpdateRequest = {} as UserUpdateRequest;

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
})
export class ProfileComponent implements OnInit {

  success = false;
  account: Account | null = null;

  profileForm = new FormGroup({
    fullname: new FormControl(initialAccount.fullname, {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(1), Validators.maxLength(50)],
    }),
    phoneNumber: new FormControl(initialAccount.phoneNumber, {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(9), Validators.maxLength(11), Validators.pattern("[0-9]{10}")],
    })
  });

  constructor(private accountService: AccountService,private sanitizer: DomSanitizer) {}

  profileImg : any;

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.account = account;
        this.profileForm.patchValue(account);
      }
    });

    this.accountService.profileImage()
      .subscribe((blob : any) => {
        let objectURL = URL.createObjectURL(blob);
        this.profileImg = this.sanitizer.bypassSecurityTrustUrl(objectURL);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.success = false;

    const account = this.profileForm.getRawValue();
    this.accountService.save(account).subscribe(() => {
      this.success = true;
      // this.accountService.authenticate(account);
    });
  }

}
