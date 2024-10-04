import { Component } from '@angular/core';
import { OrderDetailDTO, OrderRequestDTO } from '../../order-management/order-management.model';
import { DraftOrderManagementService } from '../service/draft-order-management.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DraftOrderRequestDTO, DraftOrderViewDTO } from '../draft-order-management.model';
import { OrderManagementCheckoutDialogComponent } from '../../order-management/checkout/order-management-checkout-dialog.component';
import { OrderManagementSearchDialogComponent } from '../../order-management/search/order-management-search-dialog.component';
import { ActivatedRoute } from '@angular/router';
import { CustomerDTO, CustomerDetailDTO } from '../../customer-management/customer-management.model';
import { CustomerManagementService } from '../../customer-management/service/customer-management.service';
import { CustomerCreateDialogComponent } from '../../order-management/customer/customer-create-dialog.component';
import { OrderManagementService } from '../../order-management/service/order-management.service';

@Component({
  selector: 'app-draft-order-management-update',
  templateUrl: './draft-order-management-update.component.html',
})
export class DraftOrderManagementUpdateComponent {

  isSaving = false;
  numberItems:number = 0;
  subTotal: number = 0;
  totalCost:number = 0;
  orderProducts : OrderDetailDTO[]  = [];
  draftOrder : DraftOrderViewDTO | null = null;
  note: string = '';
  draftOrderId: number | null = null;

  constructor(
    private draftOrderService: DraftOrderManagementService,
    private modalService: NgbModal,
    private route: ActivatedRoute,
    private customerService: CustomerManagementService,
    private orderService:OrderManagementService
  ) {}

  customers: CustomerDTO[] = [];
  customer: CustomerDetailDTO | null = null;

  showPopover: boolean = false;

  addCustomer(): void {
    const modalRef = this.modalService.open(
      CustomerCreateDialogComponent,
      { size: 'lg', backdrop: 'static' }
    );

    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe((reason) => {
      if (reason === 'success') {
      }
    });
  }

  togglePopover() {
    this.showPopover = !this.showPopover;
  }

  selectCustomer(customer: any) {
    this.customerService.find(customer.id).subscribe(customer => this.customer = customer);
  }

  cancelCustomer() {
    this.customer = null;
  }

  ngOnInit(): void {
    this.route.data.subscribe(({ draftOrder }) => {
      this.draftOrder = draftOrder;
      this.draftOrderId = draftOrder.id;
      this.note = draftOrder.note;
      this.orderProducts = draftOrder.orderDetails ?? [];
      this.updateOrderSummary();
      this.customer = draftOrder.customerDetail;
    });
    this.customerService.customers().subscribe(customers => this.customers = customers);
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    const draftOrder: DraftOrderRequestDTO  = {
      items: this.orderProducts,
      note: this.note
    };

    if(this.draftOrderId) {
      this.draftOrderService.update(draftOrder,this.draftOrderId).subscribe({
        next: () =>{
          this.onSaveSuccess();
        },
        error: () => this.onSaveError(),
      });
    }
  }

  createOrder(paymentStatus:string,paymentMethod: string) : void {
    const order: OrderRequestDTO = {
      items: this.orderProducts,
      note: this.note,
      paymentMethod: paymentMethod,
      paymentStatus: paymentStatus,
      customerDetail: this.customer
    }

    this.orderService.create(order).subscribe({
      next: () =>{
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

  searchProduct(): void {
    const modalRef = this.modalService.open(
      OrderManagementSearchDialogComponent,
      { size: 'lg', backdrop: 'static' }
    );
    modalRef.componentInstance.checkboxes = this.orderProducts;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe((reason) => {
      if (reason && reason.status === 'success') {
        this.orderProducts = reason.data;
        this.orderProducts.forEach(product => {
          if (!product.numberItems) {
            product.numberItems = 1;
          }
          product.unitPrice = product.numberItems * product.sellingPrice;
        });
        this.updateOrderSummary();
      }
    });
  }

  checkout(paymentStatus: string): void {
    const modalRef = this.modalService.open(
      OrderManagementCheckoutDialogComponent,
      { size: 'lg', backdrop: 'static' }
    );
    modalRef.componentInstance.totals = this.totalCost;
    modalRef.componentInstance.paymentStatus = paymentStatus;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe((reason) => {
      if (reason && reason.status === 'confirm') {
        let paymentStatus = reason.paymentStatus;
        let paymentMethod = reason.paymentMethod;
        this.createOrder(paymentStatus,paymentMethod)
      }
    });
  }

  removeProduct(id:number) :void {
    //Delete product in order list
    const index = this.orderProducts.findIndex(product => product.idProduct === id);
    if(index !== -1) {
      this.orderProducts.splice(index,1);
    }
    this.updateOrderSummary();
  }

  updateOrderSummary() :void {
    //Calculate Sum: numberItems and unitPrice
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

  inputQuantity(id: number, inputElement: HTMLInputElement) {
    let intValue = parseInt(inputElement.value);

    // Chuyển đổi số nguyên âm thành số dương
    if (intValue < 0) {
      inputElement.value = (-intValue).toString();
      intValue = -intValue;
    }

    if (inputElement.value.length > 2) {
      inputElement.value = inputElement.value.slice(0, 2);
      intValue = parseInt(inputElement.value);
    }

    this.handleUpdateQuantity({ id, quantity: intValue });
  }

  handleUpdateQuantity(p: { id: number; quantity: number }) {
    const product = this.orderProducts.find(product => product.idProduct === p.id);
    if (product) {
      product.numberItems = p.quantity || 0;
      product.unitPrice = product.numberItems * product.sellingPrice;
    }

    this.updateOrderSummary();
  }

}

