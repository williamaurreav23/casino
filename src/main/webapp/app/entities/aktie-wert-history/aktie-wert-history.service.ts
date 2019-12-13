import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAktieWertHistory } from 'app/shared/model/aktie-wert-history.model';

type EntityResponseType = HttpResponse<IAktieWertHistory>;
type EntityArrayResponseType = HttpResponse<IAktieWertHistory[]>;

@Injectable({ providedIn: 'root' })
export class AktieWertHistoryService {
  public resourceUrl = SERVER_API_URL + 'api/aktie-wert-histories';

  constructor(protected http: HttpClient) {}

  create(aktieWertHistory: IAktieWertHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(aktieWertHistory);
    return this.http
      .post<IAktieWertHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(aktieWertHistory: IAktieWertHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(aktieWertHistory);
    return this.http
      .put<IAktieWertHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAktieWertHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAktieWertHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(aktieWertHistory: IAktieWertHistory): IAktieWertHistory {
    const copy: IAktieWertHistory = Object.assign({}, aktieWertHistory, {
      creationTime:
        aktieWertHistory.creationTime != null && aktieWertHistory.creationTime.isValid() ? aktieWertHistory.creationTime.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.creationTime = res.body.creationTime != null ? moment(res.body.creationTime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((aktieWertHistory: IAktieWertHistory) => {
        aktieWertHistory.creationTime = aktieWertHistory.creationTime != null ? moment(aktieWertHistory.creationTime) : null;
      });
    }
    return res;
  }
}
