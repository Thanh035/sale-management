import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CustomerDetailDTO, CustomerUpdateDTO } from '../customer-management.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { CustomerManagementService } from '../service/customer-management.service';

const customerTemplate = {} as CustomerUpdateDTO;

@Component({
  selector: 'app-customer-contact-dialog',
  templateUrl: './customer-contact-dialog.component.html',
})
export class CustomerContactDialogComponent implements OnInit {

  constructor(
    private activeModal: NgbActiveModal,
    private customerService: CustomerManagementService
  ) {}

  customerForm!: FormGroup;
  customer : CustomerDetailDTO | null = null;
  customerId: number | null = null;

  ngOnInit(): void {
    this.customerForm = new FormGroup({
      fullName: new FormControl(this.customer?.fullName, [Validators.required, Validators.minLength(3)]),
      email: new FormControl(this.customer?.email, [Validators.required, Validators.email]),
      mobile: new FormControl(this.customer?.mobile, [Validators.required, Validators.pattern('^[0-9]{10}$')])
    });
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  save(): void {
    const customer = this.customerForm.getRawValue();
    if(this.customerId) {
      this.customerService.update(customer,this.customerId).subscribe({
        next: () => this.activeModal.close('success')
      });
    }
  }

}
