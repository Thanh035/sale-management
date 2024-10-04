import { Component, OnInit } from '@angular/core';
import { CustomerDTO } from '../customer-management.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CustomerManagementService } from '../service/customer-management.service';

@Component({
  selector: 'app-customer-management-delete-dialog',
  templateUrl: './customer-management-delete-dialog.component.html',
})
export class CustomerManagementDeleteDialogComponent implements OnInit {

  customerId? : number;
  customerName? : String;

  customers?: CustomerDTO[];

  constructor(private customerService: CustomerManagementService, private activeModal: NgbActiveModal) {}
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.customerService.deleteCustomer(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }

  deleteCustomers(): void {
    const ids = this.customers?.map(customer => customer.id) || [];
    if(ids != null) {
      this.customerService.deleteCustomers(ids).subscribe(() => {
        this.activeModal.close('deleted');
      });
    }
  }

  deleteCustomer(): void {
    const id = this.customerId;
    if(id != null) {
      this.customerService.deleteCustomer(id).subscribe(() => {
        this.activeModal.close('deleted');
      });
    }
  }

}
