import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRistourne } from 'app/shared/model/Finance/ristourne.model';

type EntityResponseType = HttpResponse<IRistourne>;
type EntityArrayResponseType = HttpResponse<IRistourne[]>;

@Injectable({ providedIn: 'root' })
export class RistourneService {
  public resourceUrl = SERVER_API_URL + 'services/finance/api/ristournes';

  constructor(protected http: HttpClient) {}

  create(ristourne: IRistourne): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ristourne);
    return this.http
      .post<IRistourne>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(ristourne: IRistourne): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ristourne);
    return this.http
      .put<IRistourne>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRistourne>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRistourne[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(ristourne: IRistourne): IRistourne {
    const copy: IRistourne = Object.assign({}, ristourne, {
      dateAttribution: ristourne.dateAttribution && ristourne.dateAttribution.isValid() ? ristourne.dateAttribution.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateAttribution = res.body.dateAttribution ? moment(res.body.dateAttribution) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((ristourne: IRistourne) => {
        ristourne.dateAttribution = ristourne.dateAttribution ? moment(ristourne.dateAttribution) : undefined;
      });
    }
    return res;
  }
}
