import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPanierBonus } from 'app/shared/model/ClientSales/panier-bonus.model';

type EntityResponseType = HttpResponse<IPanierBonus>;
type EntityArrayResponseType = HttpResponse<IPanierBonus[]>;

@Injectable({ providedIn: 'root' })
export class PanierBonusService {
  public resourceUrl = SERVER_API_URL + 'services/clientsales/api/panier-bonuses';

  constructor(protected http: HttpClient) {}

  create(panierBonus: IPanierBonus): Observable<EntityResponseType> {
    return this.http.post<IPanierBonus>(this.resourceUrl, panierBonus, { observe: 'response' });
  }

  update(panierBonus: IPanierBonus): Observable<EntityResponseType> {
    return this.http.put<IPanierBonus>(this.resourceUrl, panierBonus, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPanierBonus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPanierBonus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
