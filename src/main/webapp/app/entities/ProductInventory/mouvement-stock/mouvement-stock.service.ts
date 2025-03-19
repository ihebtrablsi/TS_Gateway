import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMouvementStock } from 'app/shared/model/ProductInventory/mouvement-stock.model';

type EntityResponseType = HttpResponse<IMouvementStock>;
type EntityArrayResponseType = HttpResponse<IMouvementStock[]>;

@Injectable({ providedIn: 'root' })
export class MouvementStockService {
  public resourceUrl = SERVER_API_URL + 'services/productinventory/api/mouvement-stocks';

  constructor(protected http: HttpClient) {}

  create(mouvementStock: IMouvementStock): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mouvementStock);
    return this.http
      .post<IMouvementStock>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(mouvementStock: IMouvementStock): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mouvementStock);
    return this.http
      .put<IMouvementStock>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMouvementStock>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMouvementStock[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(mouvementStock: IMouvementStock): IMouvementStock {
    const copy: IMouvementStock = Object.assign({}, mouvementStock, {
      dateMouvement:
        mouvementStock.dateMouvement && mouvementStock.dateMouvement.isValid() ? mouvementStock.dateMouvement.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateMouvement = res.body.dateMouvement ? moment(res.body.dateMouvement) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((mouvementStock: IMouvementStock) => {
        mouvementStock.dateMouvement = mouvementStock.dateMouvement ? moment(mouvementStock.dateMouvement) : undefined;
      });
    }
    return res;
  }
}
