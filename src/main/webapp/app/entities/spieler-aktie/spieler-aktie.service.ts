import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISpielerAktie } from 'app/shared/model/spieler-aktie.model';

type EntityResponseType = HttpResponse<ISpielerAktie>;
type EntityArrayResponseType = HttpResponse<ISpielerAktie[]>;

@Injectable({ providedIn: 'root' })
export class SpielerAktieService {
  public resourceUrl = SERVER_API_URL + 'api/spieler-akties';

  constructor(protected http: HttpClient) {}

  create(spielerAktie: ISpielerAktie): Observable<EntityResponseType> {
    return this.http.post<ISpielerAktie>(this.resourceUrl, spielerAktie, { observe: 'response' });
  }

  update(spielerAktie: ISpielerAktie): Observable<EntityResponseType> {
    return this.http.put<ISpielerAktie>(this.resourceUrl, spielerAktie, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISpielerAktie>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISpielerAktie[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
