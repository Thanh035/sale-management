import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductDTO } from '../product-management.model';
import { ProductManagementService } from '../service/product-management.service';
import { ITEMS_PER_PAGE } from 'src/app/config/pagination.constants';
import { combineLatest } from 'rxjs';
import { ASC, DESC, SORT } from 'src/app/config/navigation.constants';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ProductManagementDeleteDialogComponent } from '../delete/product-management-delete-dialog.component';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-product-management',
  templateUrl: './product-management.component.html',
})
export class ProductManagementComponent {
  products: ProductDTO[] | null = null;
  totalItems = 0;
  itemsPerPage: number = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  filter!: '';

  constructor(
    private productService: ProductManagementService,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.handleNavigation();
  }

  loadChanged() {
    this.loadAll();
  }

  trackIdentity(_index: number, item: ProductDTO): number {
    return item.id!;
  }

  transition(): void {
    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute.parent,
      queryParams: {
        page: this.page,
        sort: `${this.predicate},${this.ascending ? ASC : DESC}`,
        filter: this.filter
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

  deleteProduct(product: ProductDTO): void {
    const modalRef = this.modalService.open(
      ProductManagementDeleteDialogComponent,
      { size: 'lg', backdrop: 'static' }
    );
    modalRef.componentInstance.product = product;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe((reason) => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }

  startIndex(): number {
    return (this.page - 1) * this.itemsPerPage + 1;
  }

  endIndex(): number {
    let endIndex = this.page * this.itemsPerPage;
    endIndex = endIndex > this.totalItems ? this.totalItems : endIndex;
    return endIndex;
  }

  productsImg :any[] | null = null;

  loadAll(): void {
    this.productService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
        filter: this.filter,
      })
      .subscribe({
        next: (res: HttpResponse<ProductDTO[]>) => {
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

  private async onSuccess(products: ProductDTO[] | null, headers: HttpHeaders): Promise<void> {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.products = products;
}

 getProductImage(productId: number): any {
  this.productService.getProductImage(productId).subscribe(
    (blob: any) => {
      let objectURL = URL.createObjectURL(blob);
      console.log(productId)
      return this.sanitizer.bypassSecurityTrustUrl(objectURL);
    }
  );
}

  deleteProducts(): void {
    const modalRef = this.modalService.open(
      ProductManagementDeleteDialogComponent,
      { size: 'lg', backdrop: 'static' }
    );
    modalRef.componentInstance.products = this.checkboxes;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe((reason) => {
      if (reason === 'deleted') {
        this.checkboxes = [];
        this.loadAll();
      }
    });
  }

  //select all checkboxes
  checkboxes: ProductDTO[] = [];
  selectAll = false;

  toggleSelectAll(event: any) {
    const isChecked = event.target.checked;
    if(this.products) {
      this.checkboxes = isChecked ? [...this.products] : [];
      this.selectAll = true;
    }
  }

  checkboxChanged(product: ProductDTO) {
    if (this.checkboxes.find(c => c.id === product.id)) {
      this.checkboxes = this.checkboxes.filter(c => c.id !== product.id);
    }
    else  this.checkboxes.push(product);


    if(this.isAllCheckboxesSelected()) this.selectAll = true;
    else this.selectAll = false;
  }

  isCheckboxSelected(product: ProductDTO): boolean {
    return this.checkboxes.some(c => c.id === product.id);
  }

  isAllCheckboxesSelected() {
    return this.checkboxes.length === this.products?.length;
  }

}
