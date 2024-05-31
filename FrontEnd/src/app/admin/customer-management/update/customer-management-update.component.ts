import { Component, OnInit } from '@angular/core';
import { CustomerManagementService } from '../service/customer-management.service';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CustomerDetailDTO } from '../customer-management.model';
import { CustomerContactDialogComponent } from '../contact/customer-contact-dialog.component';
import { CustomerAddressDialogComponent } from '../address/customer-address-dialog.component';
import { CustomerAddressInfoDialogComponent } from '../address-info/customer-address-info-dialog.component';

@Component({
  selector: 'app-customer-management-update',
  templateUrl: './customer-management-update.component.html',
})
export class CustomerManagementUpdateComponent implements OnInit {

  customer: CustomerDetailDTO | null = null;
  customerId: number | null = null;

  constructor(
    private customerService: CustomerManagementService,
    private route: ActivatedRoute,
    private modalService: NgbModal,
  ) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ customer }) => {
      this.customer = customer;
      this.customerId = customer.id;
    });
  }

  customerContact(): void {
    const modalRef = this.modalService.open(
      CustomerContactDialogComponent,
      { size: 'lg', backdrop: 'static' }
    );
    modalRef.componentInstance.customer = this.customer;
    modalRef.componentInstance.customerId = this.customerId;

    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe((reason) => {
      if (reason === 'success') {
        this.loadAll();
      }
    });
  }

  customerAddress(): void {
    const modalRef = this.modalService.open(
      CustomerAddressDialogComponent,
      { size: 'lg', backdrop: 'static' }
    );
    modalRef.componentInstance.customer = this.customer;
    modalRef.componentInstance.customerId = this.customerId;

    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe((reason) => {
      if (reason === 'success') {
        this.loadAll();
      }
    });
  }

  customerAddressInfo(): void {
    const modalRef = this.modalService.open(
      CustomerAddressInfoDialogComponent,
      { size: 'lg', backdrop: 'static' }
    );
    modalRef.componentInstance.customer = this.customer;
    modalRef.componentInstance.customerId = this.customerId;

    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe((reason) => {
      if (reason === 'success') {
        this.customerAddress();
      }
    });
  }

  loadAll(): void {
    if(this.customerId) {
      this.customerService.find(this.customerId).subscribe(customer => (this.customer = customer!));
    }
  }

}
