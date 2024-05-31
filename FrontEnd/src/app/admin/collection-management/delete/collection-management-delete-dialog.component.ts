import { Component } from '@angular/core';
import { CollectionManagementService } from '../service/collection-management.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { CollectionDTO } from '../collection-management.model';

@Component({
  selector: 'app-collection-management-delete-dialog',
  templateUrl: './collection-management-delete-dialog.component.html',
})
export class CollectionManagementDeleteDialogComponent {

  // collection?: Collection;
  collectionId? : number;
  collectionName? : String;

  collections?: CollectionDTO[];

  constructor(private collectionService: CollectionManagementService, private activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.collectionService.deleteCollection(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }

  deleteCollections(): void {
    const ids = this.collections?.map(collection => collection.id) || [];
    if(ids != null) {
      this.collectionService.deleteCollections(ids).subscribe(() => {
        this.activeModal.close('deleted');
      });
    }
  }

  deleteCollection(): void {
    const id = this.collectionId;
    if(id != null) {
      this.collectionService.deleteCollection(id).subscribe(() => {
        this.activeModal.close('deleted');
      });
    }
  }

}
