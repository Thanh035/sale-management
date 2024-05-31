import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'src/app/core/config/application-config.service';
import { CollectionDTO, CollectionDetailDTO, CollectionRequestDTO } from '../collection-management.model';
import { Observable } from 'rxjs';
import { createHttpOptions, createRequestOption } from 'src/app/core/request/request-util';
import { Pagination } from 'src/app/core/request/request.model';

@Injectable({
  providedIn: 'root'
})
export class CollectionManagementService {

  private resourceUrl = this.applicationConfigService.getEndpointFor('api/v1.0/admin/collections');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) { }

  create(newCollection: CollectionRequestDTO): Observable<CollectionDTO> {
    return this.http.post<CollectionDTO>(this.resourceUrl, newCollection);
  }

  update(collection: CollectionRequestDTO,collectionId:number): Observable<CollectionDTO> {
    return this.http.put<CollectionDTO>(`${this.resourceUrl}/${collectionId}`, collection);
  }

  find(id: number): Observable<CollectionDetailDTO> {
    return this.http.get<CollectionDetailDTO>(`${this.resourceUrl}/${id}`);
  }

  query(req?: Pagination): Observable<HttpResponse<CollectionDTO[]>> {
    const options = createRequestOption(req);
    return this.http.get<CollectionDTO[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  deleteCollection(id: number): Observable<{}> {
    return this.http.delete(`${this.resourceUrl}/${id}`);
  }

  deleteCollections(ids: number[]): Observable<{}> {
    const options = createHttpOptions(ids);
    return this.http.delete(this.resourceUrl, options);
  }

  updateProducts(productIds:number[],collectionId:number): Observable<{}> {
    return this.http.put(`${this.resourceUrl}/${collectionId}/update-products`, productIds);
  }

}
