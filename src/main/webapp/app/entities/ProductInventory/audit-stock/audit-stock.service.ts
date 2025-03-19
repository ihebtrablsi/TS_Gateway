import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAuditStock } from 'app/shared/model/ProductInventory/audit-stock.model';

type EntityResponseType = HttpResponse<IAuditStock>;
type EntityArrayResponseType = HttpResponse<IAuditStock[]>;

@Injectable({ providedIn: 'root' })
export class AuditStockService {
  public resourceUrl = SERVER_API_URL + 'services/productinventory/api/audit-stocks';

  constructor(protected http: HttpClient) {}

  create(auditStock: IAuditStock): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditStock);
    return this.http
      .post<IAuditStock>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(auditStock: IAuditStock): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auditStock);
    return this.http
      .put<IAuditStock>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAuditStock>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAuditStock[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(auditStock: IAuditStock): IAuditStock {
    const copy: IAuditStock = Object.assign({}, auditStock, {
      dateAudit: auditStock.dateAudit && auditStock.dateAudit.isValid() ? auditStock.dateAudit.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateAudit = res.body.dateAudit ? moment(res.body.dateAudit) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((auditStock: IAuditStock) => {
        auditStock.dateAudit = auditStock.dateAudit ? moment(auditStock.dateAudit) : undefined;
      });
    }
    return res;
  }
}
