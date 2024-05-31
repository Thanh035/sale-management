import { Component } from '@angular/core';
import { DraftOrderDTO } from '../draft-order-management.model';
import { DraftOrderManagementService } from '../service/draft-order-management.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-draft-order-management-delete-dialog',
  templateUrl: './draft-order-management-delete-dialog.component.html',
})
export class DraftOrderManagementDeleteDialogComponent {
 draftOrderId? : number;
 code? : String;
 draftOrders?: DraftOrderDTO[];

 constructor(private draftOrderService: DraftOrderManagementService, private activeModal: NgbActiveModal) {}

 cancel(): void {
   this.activeModal.dismiss();
 }

 confirmDelete(id: number): void {
   this.draftOrderService.deleteDraftOrder(id).subscribe(() => {
     this.activeModal.close('deleted');
   });
 }

 deleteDraftOrders(): void {
  const ids = this.draftOrders?.map(draftOrder => draftOrder.id) || [];
  console.log(ids)
   if(ids != null) {
     this.draftOrderService.deleteDraftOrders(ids).subscribe(() => {
       this.activeModal.close('deleted');
     });
   }
 }

 deleteDraftOrder(): void {
   const id = this.draftOrderId;
   if(id != null) {
     this.draftOrderService.deleteDraftOrder(id).subscribe(() => {
       this.activeModal.close('deleted');
     });
   }
 }

}

