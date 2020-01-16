import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPlayerMoneyTransaction } from 'app/shared/model/player-money-transaction.model';

type EntityResponseType = HttpResponse<IPlayerMoneyTransaction>;
type EntityArrayResponseType = HttpResponse<IPlayerMoneyTransaction[]>;

@Injectable({ providedIn: 'root' })
export class PlayerMoneyTransactionService {
  public resourceUrl = SERVER_API_URL + 'api/player-money-transactions';

  constructor(protected http: HttpClient) {}

  create(playerMoneyTransaction: IPlayerMoneyTransaction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(playerMoneyTransaction);
    return this.http
      .post<IPlayerMoneyTransaction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(playerMoneyTransaction: IPlayerMoneyTransaction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(playerMoneyTransaction);
    return this.http
      .put<IPlayerMoneyTransaction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPlayerMoneyTransaction>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPlayerMoneyTransaction[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(playerMoneyTransaction: IPlayerMoneyTransaction): IPlayerMoneyTransaction {
    const copy: IPlayerMoneyTransaction = Object.assign({}, playerMoneyTransaction, {
      time: playerMoneyTransaction.time && playerMoneyTransaction.time.isValid() ? playerMoneyTransaction.time.toJSON() : undefined
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
      res.body.forEach((playerMoneyTransaction: IPlayerMoneyTransaction) => {
        playerMoneyTransaction.time = playerMoneyTransaction.time ? moment(playerMoneyTransaction.time) : undefined;
      });
    }
    return res;
  }
}
