import { Component } from '@angular/core';
import { CollectionDTO } from '../collection-management.model';
import { ITEMS_PER_PAGE } from 'src/app/config/pagination.constants';
import { CollectionManagementService } from '../service/collection-management.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { combineLatest } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ASC, DESC, SORT } from 'src/app/config/navigation.constants';
import { CollectionManagementDeleteDialogComponent } from '../delete/collection-management-delete-dialog.component';

@Component({
  selector: 'app-collection-management',
  templateUrl: './collection-management.component.html',
})
export class CollectionManagementComponent {

  collections: CollectionDTO[] | null = null;
  totalItems = 0;
  itemsPerPage: number = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  filter!: '';

  constructor(
    private collectionService: CollectionManagementService,
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

  trackIdentity(_index: number, item: CollectionDTO): number {
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
    this.collectionService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        filter: this.filter,
      })
      .subscribe({
        next: (res: HttpResponse<CollectionDTO[]>) => {
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

  private onSuccess(collections: CollectionDTO[] | null, headers: HttpHeaders): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.collections = collections;
  }


  deleteCollections(): void {
    const modalRef = this.modalService.open(
      CollectionManagementDeleteDialogComponent,
      { size: 'lg', backdrop: 'static' }
    );
    modalRef.componentInstance.collections = this.checkboxes;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe((reason) => {
      if (reason === 'deleted') {
        this.checkboxes = [];
        this.loadAll();
      }
    });
  }

   //select all checkboxes
   checkboxes: CollectionDTO[] = [];
   selectAll = false;

   toggleSelectAll(event: any) {
     const isChecked = event.target.checked;
     if(this.collections) {
       this.checkboxes = isChecked ? [...this.collections] : [];
       this.selectAll = true;
     }
   }

   checkboxChanged(collection: CollectionDTO) {
     if (this.checkboxes.find(c => c.id === collection.id)) {
       this.checkboxes = this.checkboxes.filter(c => c.id !== collection.id);
     }
     else  this.checkboxes.push(collection);


     if(this.isAllCheckboxesSelected()) this.selectAll = true;
     else this.selectAll = false;
   }

   isCheckboxSelected(collection: CollectionDTO): boolean {
     return this.checkboxes.some(c => c.id === collection.id);
   }

   isAllCheckboxesSelected() {
     return this.checkboxes.length === this.collections?.length;
   }
}

