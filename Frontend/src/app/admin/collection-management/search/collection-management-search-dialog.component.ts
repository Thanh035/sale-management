import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ProductManagementService } from '../../product-management/service/product-management.service';
import { ProductDTO, SalesProductDTO } from '../../product-management/product-management.model';
import { CollectionManagementService } from '../service/collection-management.service';

@Component({
  selector: 'app-collection-management-search-dialog',
  templateUrl: './collection-management-search-dialog.component.html',
})
export class CollectionManagementSearchDialogComponent implements OnInit {

  products: SalesProductDTO[] = [];
  productIds: number[] = [];
  collectionId? : number;

  constructor(
    private activeModal: NgbActiveModal,
    private productService: ProductManagementService,
    private collectionService: CollectionManagementService
  ) {}

  ngOnInit(): void {
    //Get all products
    this.productService.products()
    .subscribe(products => (
      this.products = products
    ));
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  updateProducts(): void {
    const productIds = this.checkboxes?.map(product => product.idProduct) || [];
    if(productIds && this.collectionId) {
      this.collectionService.updateProducts(productIds,this.collectionId).subscribe(() => {
        this.activeModal.close('success');
      });
    }
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

}
