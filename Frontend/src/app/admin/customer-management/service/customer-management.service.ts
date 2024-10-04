import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'src/app/core/config/application-config.service';
import { CustomerCreateDTO, CustomerDTO, CustomerDetailDTO, CustomerUpdateDTO, Districts, Provinces, Wards } from '../customer-management.model';
import { Observable } from 'rxjs';
import { Pagination } from 'src/app/core/request/request.model';
import { createHttpOptions, createRequestOption } from 'src/app/core/request/request-util';

@Injectable({
  providedIn: 'root'
})
export class CustomerManagementService {

  private resourceUrl = this.applicationConfigService.getEndpointFor('api/v1.0/admin/customers');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) { }

  find(id: number): Observable<CustomerDetailDTO> {
    return this.http.get<CustomerDetailDTO>(`${this.resourceUrl}/${id}`);
  }

  query(req?: Pagination): Observable<HttpResponse<CustomerDTO[]>> {
    const options = createRequestOption(req);
    return this.http.get<CustomerDTO[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  create(newCustomer: CustomerCreateDTO): Observable<CustomerCreateDTO> {
    return this.http.post<CustomerCreateDTO>(this.resourceUrl, newCustomer);
  }

  update(customer: CustomerUpdateDTO,customerId:number): Observable<CustomerDTO> {
    return this.http.put<CustomerDTO>(`${this.resourceUrl}/${customerId}`, customer);
  }

  provinces(): Observable<Provinces[]> {
    return this.http.get<Provinces[]>(this.applicationConfigService.getEndpointFor('api/v1.0/provinces'));
  }

  districts(code: string): Observable<Districts[]> {
    return this.http.get<Districts[]>(this.applicationConfigService.getEndpointFor('api/v1.0/districts/'+code));
  }

  wards(code:string): Observable<Wards[]> {
    return this.http.get<Wards[]>(this.applicationConfigService.getEndpointFor('api/v1.0/wards/' + code));
  }

  customers(): Observable<CustomerDTO[]> {
    return this.http.get<CustomerDTO[]>(this.applicationConfigService.getEndpointFor('api/v1.0/customers'));
  }

  deleteCustomer(id: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${id}`);
  }

  deleteCustomers(ids: number[]): Observable<{}> {
    const options = createHttpOptions(ids);
    return this.http.delete(this.resourceUrl, options);
  }

}
