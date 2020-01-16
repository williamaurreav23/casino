import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPlayerStockTransaction } from 'app/shared/model/player-stock-transaction.model';

type EntityResponseType = HttpResponse<IPlayerStockTransaction>;
type EntityArrayResponseType = HttpResponse<IPlayerStockTransaction[]>;

@Injectable({ providedIn: 'root' })
export class PlayerStockTransactionService {
  public resourceUrl = SERVER_API_URL + 'api/player-stock-transactions';

  constructor(protected http: HttpClient) {}

  create(playerStockTransaction: IPlayerStockTransaction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(playerStockTransaction);
    return this.http
      .post<IPlayerStockTransaction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(playerStockTransaction: IPlayerStockTransaction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(playerStockTransaction);
    return this.http
      .put<IPlayerStockTransaction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPlayerStockTransaction>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPlayerStockTransaction[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(playerStockTransaction: IPlayerStockTransaction): IPlayerStockTransaction {
    const copy: IPlayerStockTransaction = Object.assign({}, playerStockTransaction, {
      time: playerStockTransaction.time && playerStockTransaction.time.isValid() ? playerStockTransaction.time.toJSON() : undefined
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
      res.body.forEach((playerStockTransaction: IPlayerStockTransaction) => {
        playerStockTransaction.time = playerStockTransaction.time ? moment(playerStockTransaction.time) : undefined;
      });
    }
    return res;
  }
}
