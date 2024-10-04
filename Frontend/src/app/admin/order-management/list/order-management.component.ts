import { Component, OnInit } from '@angular/core';
import { OrderDTO } from '../order-management.model';
import { ITEMS_PER_PAGE } from 'src/app/config/pagination.constants';
import { OrderManagementService } from '../service/order-management.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { OrderManagementDeleteDialogComponent } from '../delete/order-management-delete-dialog.component';
import { ASC, DESC, SORT } from 'src/app/config/navigation.constants';
import { combineLatest } from 'rxjs';

@Component({
  selector: 'app-order-management',
  templateUrl: './order-management.component.html',
})
export class OrderManagementComponent implements OnInit {

  orders: OrderDTO[] | null = null;
  totalItems = 0;
  itemsPerPage: number = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  filter!: '';

  constructor(
    private orderService: OrderManagementService,
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

  trackIdentity(_index: number, item: OrderDTO): number {
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
    this.orderService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        filter: this.filter,
      })
      .subscribe({
        next: (res: HttpResponse<OrderDTO[]>) => {
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

  private onSuccess(orders: OrderDTO[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.orders = orders;
  }


  delete(): void {
    const modalRef = this.modalService.open(
      OrderManagementDeleteDialogComponent,
      { size: 'lg', backdrop: 'static' }
    );
    modalRef.componentInstance.orders = this.checkboxes;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe((reason) => {
      if (reason === 'deleted') {
        this.checkboxes = [];
        this.loadAll();
      }
    });
  }

   //select all checkboxes
   checkboxes: OrderDTO[] = [];
   selectAll = false;

   toggleSelectAll(event: any) {
     const isChecked = event.target.checked;
     if(this.orders) {
       this.checkboxes = isChecked ? [...this.orders] : [];
       this.selectAll = true;
     }
   }

   checkboxChanged(order: OrderDTO) {
     if (this.checkboxes.find(c => c.id === order.id)) {
       this.checkboxes = this.checkboxes.filter(c => c.id !== order.id);
     }
     else  this.checkboxes.push(order);

     if(this.isAllCheckboxesSelected()) this.selectAll = true;
     else this.selectAll = false;
   }

   isCheckboxSelected(order: OrderDTO): boolean {
     return this.checkboxes.some(c => c.id === order.id);
   }

   isAllCheckboxesSelected() {
     return this.checkboxes.length === this.orders?.length;
   }

}
