import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { UserCreateDTO } from '../user-management.model';
import { UserManagementService } from '../service/user-management.service';
import { ActivatedRoute } from '@angular/router';

const newUser: UserCreateDTO = {
  // activated: true,
} as UserCreateDTO;

@Component({
  selector: 'app-user-management-create-dialog',
  templateUrl: './user-management-create-dialog.component.html',
})
export class UserManagementCreateDialogComponent implements OnInit {

  editForm = new FormGroup({
    fullname: new FormControl(newUser.fullname, {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.maxLength(100),
        Validators.pattern('^[A-Za-zÀ-ỹà-ỹ ]+$')
      ]
    }),
    email: new FormControl(newUser.email, {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.pattern('[0-9]{10}')]
    })
  });

  constructor(
    private activeModal: NgbActiveModal,
    private userService: UserManagementService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.editForm.reset(newUser);
  }


  cancel(): void {
    this.activeModal.dismiss();
  }

  save(): void {
    const newUser = this.editForm.getRawValue();
    this.userService.create(newUser).subscribe(() => {
      this.activeModal.close('success');
    });
  }

}
