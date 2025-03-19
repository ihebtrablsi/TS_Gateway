import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IIncentive } from 'app/shared/model/Finance/incentive.model';

type EntityResponseType = HttpResponse<IIncentive>;
type EntityArrayResponseType = HttpResponse<IIncentive[]>;

@Injectable({ providedIn: 'root' })
export class IncentiveService {
  public resourceUrl = SERVER_API_URL + 'services/finance/api/incentives';

  constructor(protected http: HttpClient) {}

  create(incentive: IIncentive): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(incentive);
    return this.http
      .post<IIncentive>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(incentive: IIncentive): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(incentive);
    return this.http
      .put<IIncentive>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IIncentive>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIncentive[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(incentive: IIncentive): IIncentive {
    const copy: IIncentive = Object.assign({}, incentive, {
      dateDebut: incentive.dateDebut && incentive.dateDebut.isValid() ? incentive.dateDebut.toJSON() : undefined,
      dateFin: incentive.dateFin && incentive.dateFin.isValid() ? incentive.dateFin.toJSON() : undefined,
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
      res.body.forEach((incentive: IIncentive) => {
        incentive.dateDebut = incentive.dateDebut ? moment(incentive.dateDebut) : undefined;
        incentive.dateFin = incentive.dateFin ? moment(incentive.dateFin) : undefined;
      });
    }
    return res;
  }
}
