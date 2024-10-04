import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UserManagementService } from '../service/user-management.service';
import { ActivatedRoute } from '@angular/router';
import { IUser, User, UserUpdateDTO } from '../user-management.model';
import { PasswordResetInitService } from 'src/app/account/password-reset/init/password-reset-init.service';

const userTemplate = {} as UserUpdateDTO;

@Component({
  selector: 'app-user-management-update',
  templateUrl: './user-management-update.component.html'
})
export class UserManagementUpdateComponent {
  roles: string[] = [];
  isSaving = false;
  userId?: number | null = null;
  user : IUser | null  = null;

  editForm = new FormGroup({
    fullname: new FormControl(userTemplate.fullname, {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.maxLength(100),
        Validators.pattern('^[A-Za-zÀ-ỹà-ỹ ]+$')
      ]
    }),
    phoneNumber: new FormControl(userTemplate.phoneNumber, {
      nonNullable: true,
      validators: [
        Validators.pattern('[0-9]{10}')]
    }),
    roles: new FormControl(userTemplate.roles,{ nonNullable: true}),
  });

  constructor(
    private userService: UserManagementService,
    private route: ActivatedRoute,
    private passwordResetInitService: PasswordResetInitService,
  ) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ user }) => {
        this.editForm.reset(user);
        this.user = user;
        this.userId = user.id;
    });
    this.userService.roles().subscribe(roles => (this.roles = roles));
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const user = this.editForm.getRawValue();
    if(this.userId) {
      this.userService.update(user,this.userId).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      })
    }
  }

  success = false;

  requestReset(): void {
    if(this.user?.email) {
      this.passwordResetInitService.save(this.user.email).subscribe(() => (this.success = true));
    }
  }

  private onSaveSuccess(): void {
    this.isSaving = true;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }

}
