import { Component, OnInit } from '@angular/core';
import { SalesProductDTO } from '../../product-management/product-management.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ProductManagementService } from '../../product-management/service/product-management.service';

@Component({
  selector: 'app-order-management-search-dialog',
  templateUrl: './order-management-search-dialog.component.html',
})
export class OrderManagementSearchDialogComponent implements OnInit {

  products: SalesProductDTO[] = [];

  constructor(
    private activeModal: NgbActiveModal,
    private productService: ProductManagementService,
  ) {}

  ngOnInit(): void {
    //Get all products available
    this.productService.getProductsAvailable()
    .subscribe(products => (
      this.products = products
    ));
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  checkboxes: SalesProductDTO[] = [];

  checkboxChanged(product: SalesProductDTO) {
    if (this.checkboxes.find(c => c.idProduct === product.idProduct)) {
      this.checkboxes = this.checkboxes.filter(c => c.idProduct !== product.idProduct);
    }
    else  this.checkboxes.push(product);
  }

  isCheckboxSelected(product: SalesProductDTO): boolean {
    return this.checkboxes.some(c => c.idProduct === product.idProduct);
  }

  addProducts(): void {
    this.activeModal.close({ status: 'success', data: this.checkboxes });
  }

}
