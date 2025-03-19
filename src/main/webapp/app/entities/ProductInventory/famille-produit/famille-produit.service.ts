import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFamilleProduit } from 'app/shared/model/ProductInventory/famille-produit.model';

type EntityResponseType = HttpResponse<IFamilleProduit>;
type EntityArrayResponseType = HttpResponse<IFamilleProduit[]>;

@Injectable({ providedIn: 'root' })
export class FamilleProduitService {
  public resourceUrl = SERVER_API_URL + 'services/productinventory/api/famille-produits';

  constructor(protected http: HttpClient) {}

  create(familleProduit: IFamilleProduit): Observable<EntityResponseType> {
    return this.http.post<IFamilleProduit>(this.resourceUrl, familleProduit, { observe: 'response' });
  }

  update(familleProduit: IFamilleProduit): Observable<EntityResponseType> {
    return this.http.put<IFamilleProduit>(this.resourceUrl, familleProduit, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFamilleProduit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFamilleProduit[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
