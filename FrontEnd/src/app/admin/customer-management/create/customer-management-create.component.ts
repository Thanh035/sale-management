import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CustomerCreateDTO } from '../customer-management.model';
import { CustomerManagementService } from '../service/customer-management.service';
import { ActivatedRoute } from '@angular/router';
import { faMobile } from '@fortawesome/free-solid-svg-icons';


const customerTemplate = {} as CustomerCreateDTO;

const newCustomer: CustomerCreateDTO = {

} as CustomerCreateDTO;

@Component({
  selector: 'app-customer-management-create',
  templateUrl: './customer-management-create.component.html',
})
export class CustomerManagementCreateComponent {


  isSaving = false;

    editForm = new FormGroup({
      fullName: new FormControl(customerTemplate.fullName, {
        nonNullable: true,
        validators: [Validators.maxLength(50)]
      }),
      mobile: new FormControl(customerTemplate.mobile, {
        nonNullable: true,
        validators: [Validators.maxLength(50)]
      }),
      email: new FormControl(customerTemplate.email, {
        nonNullable: true,
        validators: [Validators.maxLength(50)]
      }),

    });

    constructor(private customerService: CustomerManagementService, private route: ActivatedRoute) {}

    ngOnInit(): void {
      this.editForm.reset(newCustomer);
    }

    previousState(): void {
      window.history.back();
    }

    save(): void {
      const customer = this.editForm.getRawValue();
      this.customerService.create(customer).subscribe({
        next: () =>{
          this.onSaveSuccess();
        },
        error: () => this.onSaveError(),
      });
    }

    private onSaveSuccess(): void {
      this.isSaving = false;
      this.previousState();
    }

    private onSaveError(): void {
      this.isSaving = false;
    }

  }


