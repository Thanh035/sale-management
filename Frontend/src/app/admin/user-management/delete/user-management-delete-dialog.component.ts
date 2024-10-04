import { Component, OnInit } from '@angular/core';
import { UserManagementService } from '../service/user-management.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { User } from '../user-management.model';

@Component({
  selector: 'app-user-management-delete-dialog',
  templateUrl: './user-management-delete-dialog.component.html'
})
export class UserManagementDeleteDialogComponent {
  user?: User;
  users?: User[];

  constructor(private userService: UserManagementService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(userId: number): void {
    this.userService.delete(userId).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }

  // deleteCategories(): void {
  //   const ids = this.users?.map(user => user.id) || [];
  //   if(ids != null) {
  //     this.userService.deleteUsers(ids).subscribe(() => {
  //       this.activeModal.close('deleted');
  //     });
  //   }
  // }
}
