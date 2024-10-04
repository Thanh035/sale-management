import { Component, OnInit, PipeTransform } from '@angular/core';
import { ProductRequestDTO } from '../product-management.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ProductManagementService } from '../service/product-management.service';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';

const productTemplate = {} as ProductRequestDTO;
const newProduct: ProductRequestDTO = {
} as ProductRequestDTO;

@Component({
  selector: 'app-product-management-create',
  templateUrl: './product-management-create.component.html',
})
export class ProductManagementCreateComponent implements OnInit {
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

    sellingPrice: new FormControl(productTemplate.sellingPrice || 0),
    comparePrice: new FormControl(productTemplate.comparePrice || 0, {
    }),
    capitalPrice: new FormControl(productTemplate.capitalPrice || 0),
    quantity: new FormControl(productTemplate.quantity || 0),

    sku: new FormControl(productTemplate.sku, {
    }),
    barcode: new FormControl(productTemplate.barcode, {
    }),
    allowOrders: new FormControl(productTemplate.allowOrders),
  });

  constructor(
    private productService: ProductManagementService,
  ) {}

  ngOnInit(): void {
    newProduct.capitalPrice = 0;
    newProduct.comparePrice = 0;
    newProduct.sellingPrice = 0;
    newProduct.allowOrders = false;
    newProduct.quantity = 0;

    this.editForm.reset(newProduct);
    this.productService.categories().subscribe(categories => (this.categories = categories));
    this.productService.suppliers().subscribe(suppliers => (this.suppliers = suppliers));
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const product = this.editForm.getRawValue();
    this.productService.create(product).subscribe({
      next: (product) => {
        this.onSaveSuccess(product.id);
      },
      error: () => this.onSaveError(),
    });

  }

  private onSaveSuccess(productId: number): void {
    if(this.files.length > 0 && productId != null) {
      this.productService.upload(productId,this.files[0]).subscribe();
    }
    this.isSaving = true;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }

  files: File[] = [];

  onSelect(event: any) {
    this.files.push(...event.addedFiles);
  }

  onRemove(event :any) {
    this.files.splice(this.files.indexOf(event), 1);
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
