export interface CustomerDTO {
  id: number;
  fullName: string | null;
  address: string | null;
  email: string;
  orderCount: number;
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

  provinceName: string | null;
  districtName: string | null;
  wardName: string | null;
  phoneNumber: string | null;
}

export interface CustomerCreateDTO {
  fullName: string;
  email: string;
  mobile: string;

  address?: string | null;
  phoneNumber?:String | null;

  provinceCode?: string | null;
  districtCode?:String | null;
  wardCode?:String | null;
}

export interface CustomerUpdateDTO {
  fullName: string;
  email: string;
  mobile: string;
}

export interface CustomerUpdateDTO {
  fullName: string;
  email: string;
  mobile: string;
}

export interface Provinces {
  code: string;
  name: string;
}

export interface Districts {
  code: string;
  name: string;
}

export interface Wards {
  code: string;
  name: string;
}



