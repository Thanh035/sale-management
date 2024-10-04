import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { RegisterService } from './register.service';
import { EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE } from 'src/app/config/error.constants';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
})
export class RegisterComponent {
  @ViewChild('fullname', { static: false })
  fullname?: ElementRef;

  error = false;
  errorEmailExists = false;
  errorUserExists = false;
  success = false;

  registerForm = new FormGroup({
    fullname: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(1),
        Validators.maxLength(50)
      ],
    }),
    email: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(254),
        Validators.email
      ],
    }),
    phoneNumber: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(9), Validators.maxLength(11)]
    }),
    password: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(4), Validators.maxLength(50)],
    })
  });

  constructor(private registerService: RegisterService) {}

  ngAfterViewInit(): void {
    if (this.fullname) {
      this.fullname.nativeElement.focus();
    }
  }

  register(): void {
    this.error = false;
    this.errorEmailExists = false;
    this.errorUserExists = false;


    const { password,fullname, email, phoneNumber } = this.registerForm.getRawValue();
    const login = email;

    this.registerService
      .save({ login,fullname,phoneNumber,email, password})
      .subscribe({ next: () => (this.success = true), error: response => this.processError(response) });

  }

  private processError(response: HttpErrorResponse): void {
    if (response.status === 400 && response.error.type === LOGIN_ALREADY_USED_TYPE) {
      this.errorUserExists = true;
    } else if (response.status === 400 && response.error.type === EMAIL_ALREADY_USED_TYPE) {
      this.errorEmailExists = true;
    } else {
      this.error = true;
    }
  }
}
