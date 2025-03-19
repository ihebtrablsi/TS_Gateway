import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICode } from 'app/shared/model/ProductInventory/code.model';

type EntityResponseType = HttpResponse<ICode>;
type EntityArrayResponseType = HttpResponse<ICode[]>;

@Injectable({ providedIn: 'root' })
export class CodeService {
  public resourceUrl = SERVER_API_URL + 'services/productinventory/api/codes';

  constructor(protected http: HttpClient) {}

  create(code: ICode): Observable<EntityResponseType> {
    return this.http.post<ICode>(this.resourceUrl, code, { observe: 'response' });
  }

  update(code: ICode): Observable<EntityResponseType> {
    return this.http.put<ICode>(this.resourceUrl, code, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICode>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICode[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
