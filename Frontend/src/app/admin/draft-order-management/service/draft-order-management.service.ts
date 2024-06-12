import { Injectable } from '@angular/core';
import { DraftOrderDTO, DraftOrderRequestDTO, DraftOrderViewDTO } from '../draft-order-management.model';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from 'src/app/core/config/application-config.service';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { createHttpOptions, createRequestOption } from 'src/app/core/request/request-util';
import { Pagination } from 'src/app/core/request/request.model';

@Injectable({
  providedIn: 'root'
})
export class DraftOrderManagementService {

  private resourceUrl = this.applicationConfigService.getEndpointFor('api/v1.0/admin/draftOrders');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) { }

  find(id: number): Observable<DraftOrderViewDTO> {
    return this.http.get<DraftOrderViewDTO>(`${this.resourceUrl}/${id}`);
  }

  create(newOrder: DraftOrderRequestDTO): Observable<DraftOrderDTO> {
    return this.http.post<DraftOrderDTO>(this.resourceUrl, newOrder);
  }

  update(draftOrder: DraftOrderRequestDTO,draftOrderId:number): Observable<DraftOrderDTO> {
    return this.http.put<DraftOrderDTO>(`${this.resourceUrl}/${draftOrderId}`, draftOrder);
  }

  query(req?: Pagination): Observable<HttpResponse<DraftOrderDTO[]>> {
    const options = createRequestOption(req);
    return this.http.get<DraftOrderDTO[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  deleteDraftOrder(id: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${id}`);
  }

  deleteDraftOrders(ids: number[]): Observable<{}> {
    const options = createHttpOptions(ids);
    return this.http.delete(this.resourceUrl, options);
  }

}
