import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISpielerTransaktion } from 'app/shared/model/spieler-transaktion.model';

type EntityResponseType = HttpResponse<ISpielerTransaktion>;
type EntityArrayResponseType = HttpResponse<ISpielerTransaktion[]>;

@Injectable({ providedIn: 'root' })
export class SpielerTransaktionService {
  public resourceUrl = SERVER_API_URL + 'api/spieler-transaktions';

  constructor(protected http: HttpClient) {}

  create(spielerTransaktion: ISpielerTransaktion): Observable<EntityResponseType> {
    return this.http.post<ISpielerTransaktion>(this.resourceUrl, spielerTransaktion, { observe: 'response' });
  }

  update(spielerTransaktion: ISpielerTransaktion): Observable<EntityResponseType> {
    return this.http.put<ISpielerTransaktion>(this.resourceUrl, spielerTransaktion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISpielerTransaktion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISpielerTransaktion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
