import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Spieler } from 'app/shared/model/spieler.model';
import { SpielerService } from './spieler.service';
import { SpielerComponent } from './spieler.component';
import { SpielerDetailComponent } from './spieler-detail.component';
import { SpielerUpdateComponent } from './spieler-update.component';
import { SpielerDeletePopupComponent } from './spieler-delete-dialog.component';
import { ISpieler } from 'app/shared/model/spieler.model';

@Injectable({ providedIn: 'root' })
export class SpielerResolve implements Resolve<ISpieler> {
  constructor(private service: SpielerService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISpieler> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Spieler>) => response.ok),
        map((spieler: HttpResponse<Spieler>) => spieler.body)
      );
    }
    return of(new Spieler());
  }
}

export const spielerRoute: Routes = [
  {
    path: '',
    component: SpielerComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.spieler.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SpielerDetailComponent,
    resolve: {
      spieler: SpielerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.spieler.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SpielerUpdateComponent,
    resolve: {
      spieler: SpielerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.spieler.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SpielerUpdateComponent,
    resolve: {
      spieler: SpielerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.spieler.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const spielerPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SpielerDeletePopupComponent,
    resolve: {
      spieler: SpielerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.spieler.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
