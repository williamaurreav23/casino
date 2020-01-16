import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPlayerStock } from 'app/shared/model/player-stock.model';

type EntityResponseType = HttpResponse<IPlayerStock>;
type EntityArrayResponseType = HttpResponse<IPlayerStock[]>;

@Injectable({ providedIn: 'root' })
export class PlayerStockService {
  public resourceUrl = SERVER_API_URL + 'api/player-stocks';

  constructor(protected http: HttpClient) {}

  create(playerStock: IPlayerStock): Observable<EntityResponseType> {
    return this.http.post<IPlayerStock>(this.resourceUrl, playerStock, { observe: 'response' });
  }

  update(playerStock: IPlayerStock): Observable<EntityResponseType> {
    return this.http.put<IPlayerStock>(this.resourceUrl, playerStock, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlayerStock>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlayerStock[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
