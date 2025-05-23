import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPointDeVente } from 'app/shared/model/ClientSales/point-de-vente.model';

type EntityResponseType = HttpResponse<IPointDeVente>;
type EntityArrayResponseType = HttpResponse<IPointDeVente[]>;

@Injectable({ providedIn: 'root' })
export class PointDeVenteService {
  public resourceUrl = SERVER_API_URL + 'services/clientsales/api/point-de-ventes';

  constructor(protected http: HttpClient) {}

  create(pointDeVente: IPointDeVente): Observable<EntityResponseType> {
    return this.http.post<IPointDeVente>(this.resourceUrl, pointDeVente, { observe: 'response' });
  }

  update(pointDeVente: IPointDeVente): Observable<EntityResponseType> {
    return this.http.put<IPointDeVente>(this.resourceUrl, pointDeVente, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPointDeVente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPointDeVente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
