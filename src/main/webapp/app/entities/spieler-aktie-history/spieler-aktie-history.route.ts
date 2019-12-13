import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SpielerAktieHistory } from 'app/shared/model/spieler-aktie-history.model';
import { SpielerAktieHistoryService } from './spieler-aktie-history.service';
import { SpielerAktieHistoryComponent } from './spieler-aktie-history.component';
import { SpielerAktieHistoryDetailComponent } from './spieler-aktie-history-detail.component';
import { SpielerAktieHistoryUpdateComponent } from './spieler-aktie-history-update.component';
import { SpielerAktieHistoryDeletePopupComponent } from './spieler-aktie-history-delete-dialog.component';
import { ISpielerAktieHistory } from 'app/shared/model/spieler-aktie-history.model';

@Injectable({ providedIn: 'root' })
export class SpielerAktieHistoryResolve implements Resolve<ISpielerAktieHistory> {
  constructor(private service: SpielerAktieHistoryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISpielerAktieHistory> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SpielerAktieHistory>) => response.ok),
        map((spielerAktieHistory: HttpResponse<SpielerAktieHistory>) => spielerAktieHistory.body)
      );
    }
    return of(new SpielerAktieHistory());
  }
}

export const spielerAktieHistoryRoute: Routes = [
  {
    path: '',
    component: SpielerAktieHistoryComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.spielerAktieHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SpielerAktieHistoryDetailComponent,
    resolve: {
      spielerAktieHistory: SpielerAktieHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.spielerAktieHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SpielerAktieHistoryUpdateComponent,
    resolve: {
      spielerAktieHistory: SpielerAktieHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.spielerAktieHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SpielerAktieHistoryUpdateComponent,
    resolve: {
      spielerAktieHistory: SpielerAktieHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.spielerAktieHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const spielerAktieHistoryPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SpielerAktieHistoryDeletePopupComponent,
    resolve: {
      spielerAktieHistory: SpielerAktieHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.spielerAktieHistory.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
