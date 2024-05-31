import { Component } from '@angular/core';
import { OrderDetailDTO, OrderViewDTO } from '../order-management.model';
import { OrderManagementService } from '../service/order-management.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-order-management-update',
  templateUrl: './order-management-update.component.html',
})
export class OrderManagementUpdateComponent {

  isSaving = false;
  numberItems:number = 0;
  subTotal: number = 0;
  totalCost:number = 0;
  orderProducts : OrderDetailDTO[] = [];
  order: OrderViewDTO | null = null;
  note: string = '';
  orderId: number | null = null;

  constructor(
    private orderService: OrderManagementService,
    private modalService: NgbModal,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.data.subscribe(({ order }) => {
      this.order = order;
      this.orderId = order.id;
      this.note = order.note;
      this.orderProducts = order.orderDetails ?? [];
      this.updateOrderSummary();
    });
    console.log(this.orderProducts)
  }

  previousState(): void {
    window.history.back();
  }

  private onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError(): void {
    this.isSaving = false;
  }

  updateOrderSummary() :void {
    //Calculate Sum: numberItems and totalPrice
    let numberItems = 0;
    let subTotal = 0;
    for(const product of this.orderProducts) {
      numberItems += product.numberItems;
      subTotal += product.numberItems * product.sellingPrice;
    }
    this.numberItems = numberItems;
    this.subTotal = subTotal;
    this.totalCost = subTotal - 0;
  }

}

