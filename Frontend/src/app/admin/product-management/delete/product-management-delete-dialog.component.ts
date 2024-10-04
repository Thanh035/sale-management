import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ProductManagementService } from '../service/product-management.service';
import { ProductDTO } from '../product-management.model';

@Component({
  selector: 'app-product-management-delete-dialog',
  templateUrl: './product-management-delete-dialog.component.html',
})
export class ProductManagementDeleteDialogComponent {
  // product?: Product;
  productId? : number;
  productName? : String;

  products?: ProductDTO[];

  constructor(private productService: ProductManagementService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productService.deleteProduct(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }

  deleteProducts(): void {
    const ids = this.products?.map(product => product.id) || [];
    if(ids != null) {
      this.productService.deleteProducts(ids).subscribe(() => {
        this.activeModal.close('deleted');
      });
    }
  }

  deleteProduct(): void {
    const id = this.productId;
    if(id != null) {
      this.productService.deleteProduct(id).subscribe(() => {
        this.activeModal.close('deleted');
      });
    }
  }

}
