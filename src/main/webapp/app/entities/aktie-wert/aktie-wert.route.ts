import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AktieWert } from 'app/shared/model/aktie-wert.model';
import { AktieWertService } from './aktie-wert.service';
import { AktieWertComponent } from './aktie-wert.component';
import { AktieWertDetailComponent } from './aktie-wert-detail.component';
import { AktieWertUpdateComponent } from './aktie-wert-update.component';
import { AktieWertDeletePopupComponent } from './aktie-wert-delete-dialog.component';
import { IAktieWert } from 'app/shared/model/aktie-wert.model';

@Injectable({ providedIn: 'root' })
export class AktieWertResolve implements Resolve<IAktieWert> {
  constructor(private service: AktieWertService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAktieWert> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AktieWert>) => response.ok),
        map((aktieWert: HttpResponse<AktieWert>) => aktieWert.body)
      );
    }
    return of(new AktieWert());
  }
}

export const aktieWertRoute: Routes = [
  {
    path: '',
    component: AktieWertComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.aktieWert.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AktieWertDetailComponent,
    resolve: {
      aktieWert: AktieWertResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.aktieWert.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AktieWertUpdateComponent,
    resolve: {
      aktieWert: AktieWertResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.aktieWert.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AktieWertUpdateComponent,
    resolve: {
      aktieWert: AktieWertResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.aktieWert.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const aktieWertPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AktieWertDeletePopupComponent,
    resolve: {
      aktieWert: AktieWertResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.aktieWert.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
