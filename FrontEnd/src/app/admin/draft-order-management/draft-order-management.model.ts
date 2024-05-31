import { OrderDetailDTO } from "../order-management/order-management.model";
import { SalesProductDTO } from "../product-management/product-management.model";

export interface DraftOrderViewDTO {
  code: string;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;

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
}


