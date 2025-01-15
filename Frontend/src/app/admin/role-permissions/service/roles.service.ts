import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'src/app/core/config/application-config.service';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { createRequestOption } from 'src/app/core/request/request-util';
import { Pagination } from 'src/app/core/request/request.model';
import { RoleDTO, RoleDetailDTO } from '../role-permission.model';

@Injectable({
  providedIn: 'root'
})
export class RolesService {

  private resourceUrl = this.applicationConfigService.getEndpointFor('api/v1.0/admin/groups');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) { }

  find(id: number): Observable<RoleDetailDTO> {
    return this.http.get<RoleDetailDTO>(`${this.resourceUrl}/${id}`);
  }

  query(req?: Pagination): Observable<HttpResponse<RoleDTO[]>> {
    const options = createRequestOption(req);
    return this.http.get<RoleDTO[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
}
