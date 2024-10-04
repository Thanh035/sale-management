import { Component, OnInit } from '@angular/core';
import { CustomerManagementService } from '../../customer-management/service/customer-management.service';
import { ActivatedRoute } from '@angular/router';
import { Districts, Provinces, Wards } from '../../customer-management/customer-management.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-customer-create-dialog',
  templateUrl: './customer-create-dialog.component.html',
})
export class CustomerCreateDialogComponent implements OnInit {

  provinces: Provinces[] = [];
  districts: Districts[] = [];
  wards: Wards[] = [];

  constructor(private customerService: CustomerManagementService, private route: ActivatedRoute,private activeModal: NgbActiveModal) {}

  ngOnInit(): void {
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

  cancel(): void {
    this.activeModal.dismiss();
  }


}
