import { Component, OnInit } from '@angular/core';
import { ProductRequestDTO } from '../product-management.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ProductManagementService } from '../service/product-management.service';
import { ActivatedRoute } from '@angular/router';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import { ProductManagementDeleteDialogComponent } from '../delete/product-management-delete-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';


const productTemplate = {} as ProductRequestDTO;

@Component({
  selector: 'app-product-management-update',
  templateUrl: './product-management-update.component.html',
})

export class ProductManagementUpdateComponent implements OnInit {

  productName: number | null = null;
  productId: number | null = null;

  categories: string[] = [];
  suppliers: string[] = [];
  isSaving = false;

  editForm = new FormGroup({
    productName: new FormControl(productTemplate.productName, {
      nonNullable: true,
      validators: [
        Validators.maxLength(100),
        Validators.required
      ]
    }),

    categoryName: new FormControl(productTemplate.categoryName, { }),
    supplierName: new FormControl(productTemplate.supplierName, { }),
    description: new FormControl(productTemplate.description, {
      validators: [
        Validators.minLength(9)
      ]
    }),
    quantity: new FormControl(productTemplate.quantity || 0),
    sellingPrice: new FormControl(productTemplate.sellingPrice),
    comparePrice: new FormControl(productTemplate.comparePrice, {
    }),
    capitalPrice: new FormControl(productTemplate.capitalPrice),

    sku: new FormControl(productTemplate.sku, {
    }),
    barcode: new FormControl(productTemplate.barcode, {
    }),
    allowOrders: new FormControl(productTemplate.allowOrders),
  });


  constructor(
    private productService: ProductManagementService,
    private route: ActivatedRoute,
    private modalService: NgbModal,
  ) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ product }) => {
      this.productName = product.productName;
      this.productId = product.id;
      this.editForm.reset(product);
    });
    this.productService.categories().subscribe(categories => (this.categories = categories));
    this.productService.suppliers().subscribe(suppliers => (this.suppliers = suppliers));
    this.calProfit();
    if(this.productId != null) {
      this.productService.load(this.productId).subscribe(i => {
        const myFile = new File([i], this.productImage, {
        type: i.type,
       });
       this.files.push(myFile)
      })
    }
  }


  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const product = this.editForm.getRawValue();
    if (this.productId !== null) {
      this.productService.update(product,this.productId).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError(),
      });
    }
  }

  private uploadFile(): void {
    if(this.files.length > 0 && this.productId != null) {
      this.productService.upload(this.productId,this.files[0]).subscribe();
      this.onSaveSuccess();
    }
  }

  deleteProduct(): void {
    const modalRef = this.modalService.open(
      ProductManagementDeleteDialogComponent,
      { size: 'lg', backdrop: 'static' }
    );
    modalRef.componentInstance.productId = this.productId;
    modalRef.componentInstance.productName = this.productName;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe((reason) => {
      if (reason === 'deleted') {
        this.previousState();
      }
    });
  }

  private onSaveSuccess(): void {
    this.isSaving = true;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
    this.uploadFile();
  }

  files: File[] = [];

  onSelect(event: any) {
    this.files.push(...event.addedFiles);
  }

  onRemove(event :any) {
    this.files.splice(this.files.indexOf(event), 1);
  }

  get productImage(): string {
    return `http://localhost:8096/api/v1.0/products/${this.productId}/product-image`;
  }

  public Editor = ClassicEditor;
  profit: number = 0;

  calProfit() {
    const capitalPrice = this.editForm.get("capitalPrice")?.value || 0;
    const sellingPrice = this.editForm.get("sellingPrice")?.value || 0;
    this.profit = sellingPrice - capitalPrice;
  }

  get profitMargin(): string {
    const sellingPrice = this.editForm.get("sellingPrice")?.value;

    if (typeof sellingPrice === 'undefined' || sellingPrice === null) {
      return "--";
    }

    const profitMargin = (this.profit / sellingPrice) * 100;
    if (!isFinite(profitMargin)) {
      return "--";
    }
    return parseFloat(profitMargin.toFixed(2)) + "%";
  }

  validateInput(event: any) {
    const inputValue = event.target.value;
    const numericInput = inputValue.replace(/[^0-9]/g, '');
    if (inputValue !== numericInput) {
        event.target.value = numericInput;
    }
  }

  setDefaultIfEmpty(event: any) {
    if (!event.target.value) {
      event.target.value = '0';
    }
  }

}
