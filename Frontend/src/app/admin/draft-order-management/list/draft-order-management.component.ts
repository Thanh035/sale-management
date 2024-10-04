import { Component } from '@angular/core';
import { DraftOrderDTO } from '../draft-order-management.model';
import { ITEMS_PER_PAGE } from 'src/app/config/pagination.constants';
import { DraftOrderManagementService } from '../service/draft-order-management.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ASC, DESC, SORT } from 'src/app/config/navigation.constants';
import { combineLatest } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { DraftOrderManagementDeleteDialogComponent } from '../delete/draft-order-management-delete-dialog.component';

@Component({
  selector: 'app-draft-order-management',
  templateUrl: './draft-order-management.component.html',
})
export class DraftOrderManagementComponent  {

  draftOrders: DraftOrderDTO[] | null = null;
  totalItems = 0;
  itemsPerPage: number = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  filter!: '';

  constructor(
    private draftOrderService: DraftOrderManagementService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.handleNavigation();
    console.log(this.draftOrders)
  }

  loadChanged() {
    this.loadAll();
  }

  trackIdentity(_index: number, item: DraftOrderDTO): number {
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
    this.draftOrderService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        filter: this.filter,
      })
      .subscribe({
        next: (res: HttpResponse<DraftOrderDTO[]>) => {
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

  private onSuccess(draftOrders: DraftOrderDTO[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.draftOrders = draftOrders;
  }


  deleteDraftOrders(): void {
    const modalRef = this.modalService.open(
      DraftOrderManagementDeleteDialogComponent,
      { size: 'lg', backdrop: 'static' }
    );
    modalRef.componentInstance.draftOrders = this.checkboxes;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe((reason) => {
      if (reason === 'deleted') {
        this.checkboxes = [];
        this.loadAll();
      }
    });
  }

   //select all checkboxes
   checkboxes: DraftOrderDTO[] = [];
   selectAll = false;

   toggleSelectAll(event: any) {
     const isChecked = event.target.checked;
     if(this.draftOrders) {
       this.checkboxes = isChecked ? [...this.draftOrders] : [];
       this.selectAll = true;
     }
   }

   checkboxChanged(draftOrder: DraftOrderDTO) {
     if (this.checkboxes.find(c => c.id === draftOrder.id)) {
       this.checkboxes = this.checkboxes.filter(c => c.id !== draftOrder.id);
     }
     else  this.checkboxes.push(draftOrder);

     if(this.isAllCheckboxesSelected()) this.selectAll = true;
     else this.selectAll = false;
   }

   isCheckboxSelected(draftOrder: DraftOrderDTO): boolean {
     return this.checkboxes.some(c => c.id === draftOrder.id);
   }

   isAllCheckboxesSelected() {
     return this.checkboxes.length === this.draftOrders?.length;
   }

}
