import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CollectionRequestDTO } from '../collection-management.model';
import { CollectionManagementService } from '../service/collection-management.service';
import { ActivatedRoute } from '@angular/router';
import { CollectionManagementSearchDialogComponent } from '../search/collection-management-search-dialog.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SalesProductDTO } from '../../product-management/product-management.model';
import { ProductManagementService } from '../../product-management/service/product-management.service';
import { CollectionManagementDeleteDialogComponent } from '../delete/collection-management-delete-dialog.component';


const collectionTemplate = {} as CollectionRequestDTO;

const newCollection: CollectionRequestDTO = {

} as CollectionRequestDTO;

@Component({
  selector: 'app-collection-management-update',
  templateUrl: './collection-management-update.component.html',
})
export class CollectionManagementUpdateComponent {
  isSaving = false;
  collectionId: number | null = null;
  collectionName: number | null = null;

  products: SalesProductDTO[] | null = [];

  editForm = new FormGroup({
    name: new FormControl(collectionTemplate.name, {
      nonNullable: true,
      validators: [Validators.maxLength(50)]
    }),
    description: new FormControl(collectionTemplate.description, {
      validators: [Validators.minLength(5), Validators.maxLength(254)],
    }),
  });

  constructor(
    private collectionService: CollectionManagementService,
    private route: ActivatedRoute,
    private modalService: NgbModal,
  ) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ collection }) => {
      if (collection) {
        this.collectionId = collection.id;
        this.collectionName = collection.name;
        this.products = collection.products;
        this.editForm.reset(collection);
      } else {
        this.editForm.reset(newCollection);
      }
    });
  }

  loadAll(): void {
    if(this.collectionId) {
      this.collectionService.find(this.collectionId).subscribe(collection => (this.products = collection.products!));
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const collection = this.editForm.getRawValue();
    this.collectionService.update(collection,this.collectionId!).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }

  searchProduct(): void {
    const modalRef = this.modalService.open(
      CollectionManagementSearchDialogComponent,
      { size: 'lg', backdrop: 'static' }
    );
    modalRef.componentInstance.collectionId = this.collectionId;
    if(this.products) {
      modalRef.componentInstance.checkboxes = this.products;
    }

    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe((reason) => {
      if (reason === 'success') {
        this.loadAll();
      }
    });
  }

  deleteCollection(): void {
    const modalRef = this.modalService.open(
      CollectionManagementDeleteDialogComponent,
      { size: 'lg', backdrop: 'static' }
    );
    modalRef.componentInstance.collectionId = this.collectionId;
    modalRef.componentInstance.collectionName = this.collectionName;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe((reason) => {
      if (reason === 'deleted') {
        this.previousState();
      }
    });
  }

}
