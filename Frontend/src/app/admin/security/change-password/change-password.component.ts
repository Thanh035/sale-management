import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';
import { PasswordService } from 'src/app/account/password/password.service';
import { Account } from 'src/app/core/auth/account.model';
import { AccountService } from 'src/app/core/auth/account.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
})
export class ChangePasswordComponent implements OnInit {
  doNotMatch = false;
  error = false;
  success = false;
  account$?: Observable<Account | null>;
  passwordForm = new FormGroup({
    currentPassword: new FormControl('', { nonNullable: true, validators: Validators.required }),
    newPassword: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(4), Validators.maxLength(50)],
    }),
    confirmPassword: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(4), Validators.maxLength(50)],
    }),
  });

  constructor(
    private passwordService: PasswordService,
    private accountService: AccountService,
    private activeModal: NgbActiveModal
  ) {}

  ngOnInit(): void {
    this.account$ = this.accountService.identity();
  }

  changePassword(): void {
    this.error = false;
    this.success = false;
    this.doNotMatch = false;

    const { newPassword, confirmPassword, currentPassword } = this.passwordForm.getRawValue();
    if (newPassword !== confirmPassword) {
      this.doNotMatch = true;
    } else {
      this.passwordService.save(newPassword, currentPassword).subscribe({
        next: () => {
          (this.success = true),
          this.activeModal.close('success')
        },
        error: () => (this.error = true),
      });
    }
  }

  cancel(): void {
    this.activeModal.dismiss();
  }
}
