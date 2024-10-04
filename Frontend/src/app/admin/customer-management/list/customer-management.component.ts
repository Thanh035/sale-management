import { Component, OnInit } from '@angular/core';
import { CustomerDTO } from '../customer-management.model';
import { ITEMS_PER_PAGE } from 'src/app/config/pagination.constants';
import { CustomerManagementService } from '../service/customer-management.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { combineLatest } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { CustomerManagementDeleteDialogComponent } from '../delete/customer-management-delete-dialog.component';
import { ASC, DESC, SORT } from 'src/app/config/navigation.constants';

@Component({
  selector: 'app-customer-management',
  templateUrl: './customer-management.component.html'
})
export class CustomerManagementComponent implements OnInit {


  customers: CustomerDTO[] | null = null;
  totalItems = 0;
  itemsPerPage: number = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  filter!: '';

  constructor(
    private customerService: CustomerManagementService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.handleNavigation();
  }

  loadChanged() {
    this.loadAll();
  }

  trackIdentity(_index: number, item: CustomerDTO): number {
    return item.id!;
  }

  transition(): void {
    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute.parent,
      queryParams: {
        page: this.page,
        sort: `${this.predicate},${this.ascending ? ASC : DESC}`,
      },
    });
  }

  private handleNavigation(): void {
    combineLatest([
      this.activatedRoute.data,
      this.activatedRoute.queryParamMap,
    ]).subscribe(([data, params]) => {
      const page = params.get('page');
      this.page = +(page ?? 1);
      const sort = (params.get(SORT) ?? data['defaultSort']).split(',');
      this.predicate = sort[0];
      this.ascending = sort[1] === ASC;
      this.loadAll();
    });
  }

  loadAll(): void {
    this.customerService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        filter: this.filter,
      })
      .subscribe({
        next: (res: HttpResponse<CustomerDTO[]>) => {
          this.onSuccess(res.body, res.headers);
        },
      });
  }

  private sort(): string[] {
    const result = [`${this.predicate},${this.ascending ? ASC : DESC}`];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private onSuccess(customers: CustomerDTO[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.customers = customers;
  }


  deleteCustomers(): void {
    const modalRef = this.modalService.open(
      CustomerManagementDeleteDialogComponent,
      { size: 'lg', backdrop: 'static' }
    );
    modalRef.componentInstance.customers = this.checkboxes;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe((reason) => {
      if (reason === 'deleted') {
        this.checkboxes = [];
        this.loadAll();
      }
    });
  }

   //select all checkboxes
   checkboxes: CustomerDTO[] = [];
   selectAll = false;

   toggleSelectAll(event: any) {
     const isChecked = event.target.checked;
     if(this.customers) {
       this.checkboxes = isChecked ? [...this.customers] : [];
       this.selectAll = true;
     }
   }

   checkboxChanged(collection: CustomerDTO) {
     if (this.checkboxes.find(c => c.id === collection.id)) {
       this.checkboxes = this.checkboxes.filter(c => c.id !== collection.id);
     }
     else  this.checkboxes.push(collection);


     if(this.isAllCheckboxesSelected()) this.selectAll = true;
     else this.selectAll = false;
   }

   isCheckboxSelected(collection: CustomerDTO): boolean {
     return this.checkboxes.some(c => c.id === collection.id);
   }

   isAllCheckboxesSelected() {
     return this.checkboxes.length === this.customers?.length;
   }
}


