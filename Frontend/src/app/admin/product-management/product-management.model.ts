export interface SalesProductDTO {
  idProduct: number;
  productName: string;
  sellingPrice: number;
  quantity: number;
}

export interface ProductDTO {
  id: number;
  productName: string;

  sku?: string | null | null;
  barcode?: string | null;
  quantity: number;

  categoryName?: string | null;
  supplierName?: string | null;
}

export interface ProductDetailDTO {
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


