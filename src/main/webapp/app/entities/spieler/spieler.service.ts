import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISpieler } from 'app/shared/model/spieler.model';

type EntityResponseType = HttpResponse<ISpieler>;
type EntityArrayResponseType = HttpResponse<ISpieler[]>;

@Injectable({ providedIn: 'root' })
export class SpielerService {
  public resourceUrl = SERVER_API_URL + 'api/spielers';

  constructor(protected http: HttpClient) {}

  create(spieler: ISpieler): Observable<EntityResponseType> {
    return this.http.post<ISpieler>(this.resourceUrl, spieler, { observe: 'response' });
  }

  update(spieler: ISpieler): Observable<EntityResponseType> {
    return this.http.put<ISpieler>(this.resourceUrl, spieler, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISpieler>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISpieler[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
