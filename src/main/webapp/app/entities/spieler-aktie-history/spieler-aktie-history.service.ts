import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISpielerAktieHistory } from 'app/shared/model/spieler-aktie-history.model';

type EntityResponseType = HttpResponse<ISpielerAktieHistory>;
type EntityArrayResponseType = HttpResponse<ISpielerAktieHistory[]>;

@Injectable({ providedIn: 'root' })
export class SpielerAktieHistoryService {
  public resourceUrl = SERVER_API_URL + 'api/spieler-aktie-histories';

  constructor(protected http: HttpClient) {}

  create(spielerAktieHistory: ISpielerAktieHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(spielerAktieHistory);
    return this.http
      .post<ISpielerAktieHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(spielerAktieHistory: ISpielerAktieHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(spielerAktieHistory);
    return this.http
      .put<ISpielerAktieHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISpielerAktieHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISpielerAktieHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(spielerAktieHistory: ISpielerAktieHistory): ISpielerAktieHistory {
    const copy: ISpielerAktieHistory = Object.assign({}, spielerAktieHistory, {
      creationTime:
        spielerAktieHistory.creationTime != null && spielerAktieHistory.creationTime.isValid()
          ? spielerAktieHistory.creationTime.toJSON()
          : null
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
      res.body.forEach((spielerAktieHistory: ISpielerAktieHistory) => {
        spielerAktieHistory.creationTime = spielerAktieHistory.creationTime != null ? moment(spielerAktieHistory.creationTime) : null;
      });
    }
    return res;
  }
}
