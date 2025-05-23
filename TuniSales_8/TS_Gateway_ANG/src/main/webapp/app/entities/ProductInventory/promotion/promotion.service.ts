import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPromotion } from 'app/shared/model/ProductInventory/promotion.model';

type EntityResponseType = HttpResponse<IPromotion>;
type EntityArrayResponseType = HttpResponse<IPromotion[]>;

@Injectable({ providedIn: 'root' })
export class PromotionService {
  public resourceUrl = SERVER_API_URL + 'services/productinventory/api/promotions';

  constructor(protected http: HttpClient) {}

  create(promotion: IPromotion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(promotion);
    return this.http
      .post<IPromotion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(promotion: IPromotion): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(promotion);
    return this.http
      .put<IPromotion>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPromotion>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPromotion[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(promotion: IPromotion): IPromotion {
    const copy: IPromotion = Object.assign({}, promotion, {
      dateDebut: promotion.dateDebut && promotion.dateDebut.isValid() ? promotion.dateDebut.toJSON() : undefined,
      dateFin: promotion.dateFin && promotion.dateFin.isValid() ? promotion.dateFin.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDebut = res.body.dateDebut ? moment(res.body.dateDebut) : undefined;
      res.body.dateFin = res.body.dateFin ? moment(res.body.dateFin) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((promotion: IPromotion) => {
        promotion.dateDebut = promotion.dateDebut ? moment(promotion.dateDebut) : undefined;
        promotion.dateFin = promotion.dateFin ? moment(promotion.dateFin) : undefined;
      });
    }
    return res;
  }
}
