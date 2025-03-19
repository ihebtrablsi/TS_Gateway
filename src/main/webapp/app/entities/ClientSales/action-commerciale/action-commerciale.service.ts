import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IActionCommerciale } from 'app/shared/model/ClientSales/action-commerciale.model';

type EntityResponseType = HttpResponse<IActionCommerciale>;
type EntityArrayResponseType = HttpResponse<IActionCommerciale[]>;

@Injectable({ providedIn: 'root' })
export class ActionCommercialeService {
  public resourceUrl = SERVER_API_URL + 'services/clientsales/api/action-commerciales';

  constructor(protected http: HttpClient) {}

  create(actionCommerciale: IActionCommerciale): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(actionCommerciale);
    return this.http
      .post<IActionCommerciale>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(actionCommerciale: IActionCommerciale): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(actionCommerciale);
    return this.http
      .put<IActionCommerciale>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IActionCommerciale>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IActionCommerciale[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(actionCommerciale: IActionCommerciale): IActionCommerciale {
    const copy: IActionCommerciale = Object.assign({}, actionCommerciale, {
      dateDebut: actionCommerciale.dateDebut && actionCommerciale.dateDebut.isValid() ? actionCommerciale.dateDebut.toJSON() : undefined,
      dateFin: actionCommerciale.dateFin && actionCommerciale.dateFin.isValid() ? actionCommerciale.dateFin.toJSON() : undefined,
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
      res.body.forEach((actionCommerciale: IActionCommerciale) => {
        actionCommerciale.dateDebut = actionCommerciale.dateDebut ? moment(actionCommerciale.dateDebut) : undefined;
        actionCommerciale.dateFin = actionCommerciale.dateFin ? moment(actionCommerciale.dateFin) : undefined;
      });
    }
    return res;
  }
}
