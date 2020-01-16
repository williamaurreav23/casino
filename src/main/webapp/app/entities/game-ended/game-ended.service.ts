import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGameEnded } from 'app/shared/model/game-ended.model';

type EntityResponseType = HttpResponse<IGameEnded>;
type EntityArrayResponseType = HttpResponse<IGameEnded[]>;

@Injectable({ providedIn: 'root' })
export class GameEndedService {
  public resourceUrl = SERVER_API_URL + 'api/game-endeds';

  constructor(protected http: HttpClient) {}

  create(gameEnded: IGameEnded): Observable<EntityResponseType> {
    return this.http.post<IGameEnded>(this.resourceUrl, gameEnded, { observe: 'response' });
  }

  update(gameEnded: IGameEnded): Observable<EntityResponseType> {
    return this.http.put<IGameEnded>(this.resourceUrl, gameEnded, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGameEnded>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGameEnded[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
