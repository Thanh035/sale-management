import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { CollectionRequestDTO } from '../collection-management.model';
import { CollectionManagementService } from '../service/collection-management.service';
import { ActivatedRoute } from '@angular/router';

const collectionTemplate = {} as CollectionRequestDTO;

const newCollection: CollectionRequestDTO = {

} as CollectionRequestDTO;

@Component({
  selector: 'app-collection-management-create',
  templateUrl: './collection-management-create.component.html',
})

export class CollectionManagementCreateComponent {

  isSaving = false;
    collectionId: number | null = null;

    editForm = new FormGroup({
      name: new FormControl(collectionTemplate.name, {
        nonNullable: true,
        validators: [Validators.maxLength(50)]
      }),
      description: new FormControl(collectionTemplate.description, {
        validators: [Validators.minLength(5), Validators.maxLength(254)],
      }),
    });

    constructor(private collectionService: CollectionManagementService, private route: ActivatedRoute) {}

    ngOnInit(): void {
      this.editForm.reset(newCollection);
    }

    previousState(): void {
      window.history.back();
    }

    save(): void {
      const collection = this.editForm.getRawValue();
      this.collectionService.create(collection).subscribe({
        next: (collection) =>{
          this.collectionId = collection.id;
          this.onSaveSuccess();
        },
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

  }


