import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISuiviAction } from 'app/shared/model/ClientSales/suivi-action.model';

type EntityResponseType = HttpResponse<ISuiviAction>;
type EntityArrayResponseType = HttpResponse<ISuiviAction[]>;

@Injectable({ providedIn: 'root' })
export class SuiviActionService {
  public resourceUrl = SERVER_API_URL + 'services/clientsales/api/suivi-actions';

  constructor(protected http: HttpClient) {}

  create(suiviAction: ISuiviAction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(suiviAction);
    return this.http
      .post<ISuiviAction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(suiviAction: ISuiviAction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(suiviAction);
    return this.http
      .put<ISuiviAction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISuiviAction>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISuiviAction[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(suiviAction: ISuiviAction): ISuiviAction {
    const copy: ISuiviAction = Object.assign({}, suiviAction, {
      dateSuivi: suiviAction.dateSuivi && suiviAction.dateSuivi.isValid() ? suiviAction.dateSuivi.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateSuivi = res.body.dateSuivi ? moment(res.body.dateSuivi) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((suiviAction: ISuiviAction) => {
        suiviAction.dateSuivi = suiviAction.dateSuivi ? moment(suiviAction.dateSuivi) : undefined;
      });
    }
    return res;
  }
}
