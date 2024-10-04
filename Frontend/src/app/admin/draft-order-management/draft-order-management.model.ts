import { CustomerDetailDTO } from "../customer-management/customer-management.model";
import { OrderDetailDTO } from "../order-management/order-management.model";
import { SalesProductDTO } from "../product-management/product-management.model";

export interface DraftOrderViewDTO {
  code: string;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;

  customerDetail: CustomerDetailDTO | null;
  products? :Array<OrderDetailDTO> | null;
  note?: string;
}

export interface DraftOrderDTO {
  id: number;
  code:string;
  createdDate?: Date;
  lastModifiedDate?: Date;

  customerName: string;
  status: boolean;
  payingAmount: number
}

export interface DraftOrderRequestDTO {
  items :Array<OrderDetailDTO>;
  note? :string | null;
  customerDetail?: CustomerDetailDTO | null;
}


