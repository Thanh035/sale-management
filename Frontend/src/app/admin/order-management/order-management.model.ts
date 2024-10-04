import { CustomerDetailDTO } from "../customer-management/customer-management.model";

export interface OrderDTO {
  id: number;
  code:string;
  createdDate?: Date;
  customerName: string;
  paymentStatus: string;
  payingAmount: number
}

export interface OrderDetailDTO {
  idProduct: number;
  productName: string;
  numberItems: number;
  sellingPrice: number;
  unitPrice: number;
}

export interface ProductRequestDTO {
  productName?: string;

  categoryName?: string | null;
  supplierName?: string | null;
  description? : string | null;

  quantity: number | null;
  sellingPrice?: number | null;
  comparePrice?: number | null;
  capitalPrice?: number | null;

  sku?: string | null | null;
  barcode?: string | null;
  allowOrders? : boolean | null;
}

export interface OrderRequestDTO {
  items :Array<OrderDetailDTO>;
  note? :string | null;
  paymentMethod: string;
  paymentStatus: string;
  customerDetail:CustomerDetailDTO | null;
}

export interface OrderViewDTO {
  id :number;
  code:string;
  paymentType:string;
  lastModifiedDate:Date;
  lastModifiedBy:string;

  products? :Array<OrderDetailDTO> | null;
  note?: string;

  paymentMethod?: string;
}


