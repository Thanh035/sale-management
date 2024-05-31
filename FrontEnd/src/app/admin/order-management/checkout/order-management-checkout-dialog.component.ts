import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-order-management-checkout-dialog',
  templateUrl: './order-management-checkout-dialog.component.html',
})
export class OrderManagementCheckoutDialogComponent {

  totals? : number;
  paymentStatus?: string;

   constructor(private activeModal: NgbActiveModal) {}
   cancel(): void {
     this.activeModal.dismiss();
   }

   confirm(): void {
    this.activeModal.close({ status: 'confirm', paymentMethod: this.paymentMethod,paymentStatus:this.paymentStatus });
   }

  isDropdownOpen: boolean = false;
  selectedPaymentMethod: string = 'Chọn phương thức thanh toán';
  paymentMethod? :string;

  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  closeDropdown() {
    this.isDropdownOpen = false;
  }

  selectPaymentMethod(method: string) {
    this.paymentMethod = method;
    if(method == "cod") {
      this.selectedPaymentMethod = "Thanh toán khi giao hàng (COD)";
    }
    else if(method = "bankTransfer") {
      this.selectedPaymentMethod = "Chuyển khoản qua ngân hàng";
    }
    this.isDropdownOpen = false;
  }

}
