import { ProductDTO, SalesProductDTO } from '../product-management/product-management.model';
export interface CollectionDTO {
  id: number;
  name: string;
  productCount : number
}

export interface CollectionRequestDTO {
  name: string;
  description?: string | null;
}

export interface CollectionDetailDTO {
  id: number;
  name: string;
  description?: string | null;
  products? :Array<SalesProductDTO> | null;
}

