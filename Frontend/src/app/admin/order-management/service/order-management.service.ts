import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'src/app/core/config/application-config.service';
import { OrderDTO, OrderDetailDTO, OrderRequestDTO } from '../order-management.model';
import { Observable } from 'rxjs';
import { Pagination } from 'src/app/core/request/request.model';
import { createRequestOption } from 'src/app/core/request/request-util';

@Injectable({
  providedIn: 'root'
})
export class OrderManagementService {

  private resourceUrl = this.applicationConfigService.getEndpointFor('api/v1.0/admin/orders');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  find(id: number): Observable<OrderDetailDTO> {
    return this.http.get<OrderDetailDTO>(`${this.resourceUrl}/${id}`);
  }

  query(req?: Pagination): Observable<HttpResponse<OrderDTO[]>> {
    const options = createRequestOption(req);
    return this.http.get<OrderDTO[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  create(newOrder: OrderRequestDTO): Observable<OrderDTO> {
    return this.http.post<OrderDTO>(this.resourceUrl, newOrder);
  }

  payment(paymentMethod:String,orderId:number): Observable<OrderDTO> {
    return this.http.patch<OrderDTO>(`${this.resourceUrl}/${orderId}`, paymentMethod);
  }


}
