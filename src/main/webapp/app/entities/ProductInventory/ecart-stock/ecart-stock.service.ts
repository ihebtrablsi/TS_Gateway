import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEcartStock } from 'app/shared/model/ProductInventory/ecart-stock.model';

type EntityResponseType = HttpResponse<IEcartStock>;
type EntityArrayResponseType = HttpResponse<IEcartStock[]>;

@Injectable({ providedIn: 'root' })
export class EcartStockService {
  public resourceUrl = SERVER_API_URL + 'services/productinventory/api/ecart-stocks';

  constructor(protected http: HttpClient) {}

  create(ecartStock: IEcartStock): Observable<EntityResponseType> {
    return this.http.post<IEcartStock>(this.resourceUrl, ecartStock, { observe: 'response' });
  }

  update(ecartStock: IEcartStock): Observable<EntityResponseType> {
    return this.http.put<IEcartStock>(this.resourceUrl, ecartStock, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEcartStock>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEcartStock[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
