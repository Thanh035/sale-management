export interface IUser {
  id: number | null;
  login?: string;
  fullname?: string | null;
  phoneNumber?: string | null;
  email?: string;
  activated?: boolean;
  roles?: string[];
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
}

export class User implements IUser {
  constructor(
    public id: number,
    public login?: string,
    public fullname?: string | null,
    public phoneNumber?: string | null,
    public email?: string,
    public activated?: boolean,
    public roles?: string[],
    public createdBy?: string,
    public createdDate?: Date,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Date
  ) {}
}


export interface SaveOrUpdateUserDTO {
  login?: string;
  fullname?: string | null;
  phoneNumber?: string | null;
  email?: string;
  activated?: boolean;
  roles?: string[];
}

export interface UserUpdateDTO {
  fullname: string;
  roles: string[] | null;
  phoneNumber:string | null;
}

export interface UserCreateDTO {
  fullname: string;
  email: string;
}

export interface UserDetailDTO {
  fullname?: string | null;
  phoneNumber?: string;
  email?:string;
  roles: string[];
}

export interface UserDTO {
  login: string;
  id: number;
  fullname: string | null;
  email: string;
  activated: boolean;
  roles: string[];
  createdDate?: Date;
}
