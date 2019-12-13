import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Aktie } from 'app/shared/model/aktie.model';
import { AktieService } from './aktie.service';
import { AktieComponent } from './aktie.component';
import { AktieDetailComponent } from './aktie-detail.component';
import { AktieUpdateComponent } from './aktie-update.component';
import { AktieDeletePopupComponent } from './aktie-delete-dialog.component';
import { IAktie } from 'app/shared/model/aktie.model';

@Injectable({ providedIn: 'root' })
export class AktieResolve implements Resolve<IAktie> {
  constructor(private service: AktieService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAktie> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Aktie>) => response.ok),
        map((aktie: HttpResponse<Aktie>) => aktie.body)
      );
    }
    return of(new Aktie());
  }
}

export const aktieRoute: Routes = [
  {
    path: '',
    component: AktieComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.aktie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AktieDetailComponent,
    resolve: {
      aktie: AktieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.aktie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AktieUpdateComponent,
    resolve: {
      aktie: AktieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.aktie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AktieUpdateComponent,
    resolve: {
      aktie: AktieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.aktie.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const aktiePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AktieDeletePopupComponent,
    resolve: {
      aktie: AktieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.aktie.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
