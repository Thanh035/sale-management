export interface CustomerDTO {
  id: number | null;
  fullName: string | null;
  address: string | null;
  orderCount: string;
  lastOrder: string;
  debt: string;
  totalSpent: string
}

export interface CustomerDetailDTO {
  if: number;
  fullName: string | null;
  address: string | null;
  email: string | null;
  mobile: string | null;
}

export interface CustomerCreateDTO {
  fullName: string;
  email: string;
  mobile: string;

  address?: string | null;
  phoneNumber?:String | null;
}

export interface CustomerUpdateDTO {
  fullName: string;
  email: string;
  mobile: string;
}

// <span class="userinitials size-100" title="Quản trị viên ADMIN" aria-label="Quản trị viên ADMIN" role="img">QA</span>
