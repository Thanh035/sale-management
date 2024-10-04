import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CustomerCreateDTO, Provinces, Districts, Wards } from '../customer-management.model';
import { CustomerManagementService } from '../service/customer-management.service';
import { ActivatedRoute } from '@angular/router';


const customerTemplate = {} as CustomerCreateDTO;

const newCustomer: CustomerCreateDTO = {

} as CustomerCreateDTO;

@Component({
  selector: 'app-customer-management-create',
  templateUrl: './customer-management-create.component.html',
})
export class CustomerManagementCreateComponent {

  provinces: Provinces[] = [];
  districts: Districts[] = [];
  wards: Wards[] = [];

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
      phoneNumber: new FormControl(customerTemplate.phoneNumber, {
        validators: [Validators.maxLength(50)]
      }),
      address: new FormControl(customerTemplate.address, {
        validators: [Validators.maxLength(50)]
      }),

      provinceCode: new FormControl(customerTemplate.provinceCode, {
        validators: [Validators.maxLength(50)]
      }),
      districtCode: new FormControl(customerTemplate.districtCode, {
        validators: [Validators.maxLength(50)]
      }),
      wardCode: new FormControl(customerTemplate.wardCode, {
        validators: [Validators.maxLength(50)]
      }),

    });

    constructor(private customerService: CustomerManagementService, private route: ActivatedRoute) {}

    ngOnInit(): void {
      this.editForm.reset(newCustomer);
      this.customerService.provinces().subscribe(provinces => (this.provinces = provinces));
    }

    onProvinceChange(event: any): void {
      const provinceCode = event.target.value;
      if (provinceCode) {
        this.customerService.districts(provinceCode)
          .subscribe(districts => this.districts = districts);
      } else {
        this.districts = [];
      }
    }

    onDistrictsChange(event: any): void {
      const wardCode = event.target.value;
      if (wardCode) {
        this.customerService.wards(wardCode)
          .subscribe(wards => this.wards = wards);
      } else {
        this.wards = [];
      }
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


