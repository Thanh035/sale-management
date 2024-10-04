import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { OrderManagementService } from '../service/order-management.service';

@Component({
  selector: 'app-payment-dialog',
  templateUrl: './payment-dialog.component.html',
})
export class PaymentDialogComponent {

  totals? : number;
  orderId?: number;

   constructor(private activeModal: NgbActiveModal,private orderService:OrderManagementService) {}

   cancel(): void {
     this.activeModal.dismiss();
   }

   confirm(paymentMethod:string): void {
      // if(this.orderId) {
      //   this.orderService.payment(paymentMethod,this.orderId).subscribe(() => this.activeModal.close('success'));
      // }
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
