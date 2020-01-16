import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStockValueChange } from 'app/shared/model/stock-value-change.model';

type EntityResponseType = HttpResponse<IStockValueChange>;
type EntityArrayResponseType = HttpResponse<IStockValueChange[]>;

@Injectable({ providedIn: 'root' })
export class StockValueChangeService {
  public resourceUrl = SERVER_API_URL + 'api/stock-value-changes';

  constructor(protected http: HttpClient) {}

  create(stockValueChange: IStockValueChange): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stockValueChange);
    return this.http
      .post<IStockValueChange>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(stockValueChange: IStockValueChange): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stockValueChange);
    return this.http
      .put<IStockValueChange>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStockValueChange>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStockValueChange[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(stockValueChange: IStockValueChange): IStockValueChange {
    const copy: IStockValueChange = Object.assign({}, stockValueChange, {
      time: stockValueChange.time && stockValueChange.time.isValid() ? stockValueChange.time.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.time = res.body.time ? moment(res.body.time) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((stockValueChange: IStockValueChange) => {
        stockValueChange.time = stockValueChange.time ? moment(stockValueChange.time) : undefined;
      });
    }
    return res;
  }
}
