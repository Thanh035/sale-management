import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IUser, SaveOrUpdateUserDTO, User, UserCreateDTO, UserDTO, UserDetailDTO, UserUpdateDTO } from '../user-management.model';
import { ApplicationConfigService } from 'src/app/core/config/application-config.service';
import { createFormData, createHttpOptions, createRequestOption } from 'src/app/core/request/request-util';
import { Pagination } from 'src/app/core/request/request.model';

@Injectable({
  providedIn: 'root'
})
export class UserManagementService {

  private resourceUrl = this.applicationConfigService.getEndpointFor('api/v1.0/admin/users');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(newUser: UserCreateDTO): Observable<UserDTO> {
    return this.http.post<UserDTO>(this.resourceUrl, newUser);
  }

  update(user: UserUpdateDTO,userId:number): Observable<UserDTO> {
    return this.http.put<UserDTO>(`${this.resourceUrl}/${userId}`, user);
  }

  activate(activate:boolean,userId:number): Observable<IUser> {
    return this.http.patch<IUser>(`${this.resourceUrl}/${userId}`, activate);
  }

  find(id: number): Observable<UserDetailDTO> {
    return this.http.get<UserDetailDTO>(`${this.resourceUrl}/${id}`);
  }

  query(req?: Pagination): Observable<HttpResponse<UserDTO[]>> {
    const options = createRequestOption(req);
    return this.http.get<UserDTO[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${id}`);
  }

  roles(): Observable<string[]> {
    return this.http.get<string[]>(this.applicationConfigService.getEndpointFor('api/v1.0/roles'));
  }

  uploadImage(userId: number,file: File): Observable<{}> {
    const formData = createFormData(file);
    return this.http.post(`${this.resourceUrl}/${userId}/profile-image`,formData);
  }

  getProfileImage(): Observable<string> {
    return this.http.get<string>('http://localhost:8096/api/v1.0/account/profile-image');
  }


}
