import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SpielerTransaktion } from 'app/shared/model/spieler-transaktion.model';
import { SpielerTransaktionService } from './spieler-transaktion.service';
import { SpielerTransaktionComponent } from './spieler-transaktion.component';
import { SpielerTransaktionDetailComponent } from './spieler-transaktion-detail.component';
import { SpielerTransaktionUpdateComponent } from './spieler-transaktion-update.component';
import { SpielerTransaktionDeletePopupComponent } from './spieler-transaktion-delete-dialog.component';
import { ISpielerTransaktion } from 'app/shared/model/spieler-transaktion.model';

@Injectable({ providedIn: 'root' })
export class SpielerTransaktionResolve implements Resolve<ISpielerTransaktion> {
  constructor(private service: SpielerTransaktionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISpielerTransaktion> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SpielerTransaktion>) => response.ok),
        map((spielerTransaktion: HttpResponse<SpielerTransaktion>) => spielerTransaktion.body)
      );
    }
    return of(new SpielerTransaktion());
  }
}

export const spielerTransaktionRoute: Routes = [
  {
    path: '',
    component: SpielerTransaktionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.spielerTransaktion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SpielerTransaktionDetailComponent,
    resolve: {
      spielerTransaktion: SpielerTransaktionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.spielerTransaktion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SpielerTransaktionUpdateComponent,
    resolve: {
      spielerTransaktion: SpielerTransaktionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.spielerTransaktion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SpielerTransaktionUpdateComponent,
    resolve: {
      spielerTransaktion: SpielerTransaktionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.spielerTransaktion.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const spielerTransaktionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SpielerTransaktionDeletePopupComponent,
    resolve: {
      spielerTransaktion: SpielerTransaktionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.spielerTransaktion.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
