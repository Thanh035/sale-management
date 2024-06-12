import { Component } from '@angular/core';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CustomerAddressDialogComponent } from '../address/customer-address-dialog.component';

@Component({
  selector: 'app-customer-address-info-dialog',
  templateUrl: './customer-address-info-dialog.component.html',
})
export class CustomerAddressInfoDialogComponent {

  constructor(
    private activeModal: NgbActiveModal,
  ) {}


  cancel(): void {
    this.activeModal.dismiss();
  }

  updateAddress(): void {
    this.activeModal.close('success');
  }
}
