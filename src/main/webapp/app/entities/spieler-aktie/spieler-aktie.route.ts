import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SpielerAktie } from 'app/shared/model/spieler-aktie.model';
import { SpielerAktieService } from './spieler-aktie.service';
import { SpielerAktieComponent } from './spieler-aktie.component';
import { SpielerAktieDetailComponent } from './spieler-aktie-detail.component';
import { SpielerAktieUpdateComponent } from './spieler-aktie-update.component';
import { SpielerAktieDeletePopupComponent } from './spieler-aktie-delete-dialog.component';
import { ISpielerAktie } from 'app/shared/model/spieler-aktie.model';

@Injectable({ providedIn: 'root' })
export class SpielerAktieResolve implements Resolve<ISpielerAktie> {
  constructor(private service: SpielerAktieService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISpielerAktie> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SpielerAktie>) => response.ok),
        map((spielerAktie: HttpResponse<SpielerAktie>) => spielerAktie.body)
      );
    }
    return of(new SpielerAktie());
  }
}

export const spielerAktieRoute: Routes = [
  {
    path: '',
    component: SpielerAktieComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.spielerAktie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SpielerAktieDetailComponent,
    resolve: {
      spielerAktie: SpielerAktieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.spielerAktie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SpielerAktieUpdateComponent,
    resolve: {
      spielerAktie: SpielerAktieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.spielerAktie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SpielerAktieUpdateComponent,
    resolve: {
      spielerAktie: SpielerAktieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.spielerAktie.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const spielerAktiePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SpielerAktieDeletePopupComponent,
    resolve: {
      spielerAktie: SpielerAktieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.spielerAktie.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
