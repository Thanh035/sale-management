import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-customer-address-dialog',
  templateUrl: './customer-address-dialog.component.html',
})
export class CustomerAddressDialogComponent {

  constructor(private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

}
