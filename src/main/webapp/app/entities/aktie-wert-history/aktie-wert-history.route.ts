import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AktieWertHistory } from 'app/shared/model/aktie-wert-history.model';
import { AktieWertHistoryService } from './aktie-wert-history.service';
import { AktieWertHistoryComponent } from './aktie-wert-history.component';
import { AktieWertHistoryDetailComponent } from './aktie-wert-history-detail.component';
import { AktieWertHistoryUpdateComponent } from './aktie-wert-history-update.component';
import { AktieWertHistoryDeletePopupComponent } from './aktie-wert-history-delete-dialog.component';
import { IAktieWertHistory } from 'app/shared/model/aktie-wert-history.model';

@Injectable({ providedIn: 'root' })
export class AktieWertHistoryResolve implements Resolve<IAktieWertHistory> {
  constructor(private service: AktieWertHistoryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAktieWertHistory> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AktieWertHistory>) => response.ok),
        map((aktieWertHistory: HttpResponse<AktieWertHistory>) => aktieWertHistory.body)
      );
    }
    return of(new AktieWertHistory());
  }
}

export const aktieWertHistoryRoute: Routes = [
  {
    path: '',
    component: AktieWertHistoryComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.aktieWertHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AktieWertHistoryDetailComponent,
    resolve: {
      aktieWertHistory: AktieWertHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.aktieWertHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AktieWertHistoryUpdateComponent,
    resolve: {
      aktieWertHistory: AktieWertHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.aktieWertHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AktieWertHistoryUpdateComponent,
    resolve: {
      aktieWertHistory: AktieWertHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.aktieWertHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const aktieWertHistoryPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AktieWertHistoryDeletePopupComponent,
    resolve: {
      aktieWertHistory: AktieWertHistoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.aktieWertHistory.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
