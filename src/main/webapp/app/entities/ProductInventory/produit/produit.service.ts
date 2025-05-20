import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProduit } from 'app/shared/model/ProductInventory/produit.model';
import { catchError } from 'rxjs/operators';

type EntityResponseType = HttpResponse<IProduit>;
type EntityArrayResponseType = HttpResponse<IProduit[]>;

@Injectable({ providedIn: 'root' })
export class ProduitService {
  public resourceUrl = SERVER_API_URL + 'services/productinventory/api/produits';

  constructor(protected http: HttpClient) {}

  create(produit: IProduit): Observable<EntityResponseType> {
    return this.http.post<IProduit>(this.resourceUrl, produit, { observe: 'response' });
  }

  update(produit: IProduit): Observable<EntityResponseType> {
    return this.http.put<IProduit>(this.resourceUrl, produit, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProduit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<HttpResponse<IProduit[]>> {
    const options = createRequestOption(req);
    return this.http
      .get<IProduit[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(
        catchError(error => {
          console.error('Error fetching produits:', error);
          return throwError(error);
        })
      );
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
