import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILettrage } from 'app/shared/model/Finance/lettrage.model';

type EntityResponseType = HttpResponse<ILettrage>;
type EntityArrayResponseType = HttpResponse<ILettrage[]>;

@Injectable({ providedIn: 'root' })
export class LettrageService {
  public resourceUrl = SERVER_API_URL + 'services/finance/api/lettrages';

  constructor(protected http: HttpClient) {}

  create(lettrage: ILettrage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(lettrage);
    return this.http
      .post<ILettrage>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(lettrage: ILettrage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(lettrage);
    return this.http
      .put<ILettrage>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILettrage>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILettrage[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(lettrage: ILettrage): ILettrage {
    const copy: ILettrage = Object.assign({}, lettrage, {
      dateLettrage: lettrage.dateLettrage && lettrage.dateLettrage.isValid() ? lettrage.dateLettrage.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateLettrage = res.body.dateLettrage ? moment(res.body.dateLettrage) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((lettrage: ILettrage) => {
        lettrage.dateLettrage = lettrage.dateLettrage ? moment(lettrage.dateLettrage) : undefined;
      });
    }
    return res;
  }
}
