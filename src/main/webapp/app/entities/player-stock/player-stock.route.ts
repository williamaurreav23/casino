import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlayerStock, PlayerStock } from 'app/shared/model/player-stock.model';
import { PlayerStockService } from './player-stock.service';
import { PlayerStockComponent } from './player-stock.component';
import { PlayerStockDetailComponent } from './player-stock-detail.component';
import { PlayerStockUpdateComponent } from './player-stock-update.component';

@Injectable({ providedIn: 'root' })
export class PlayerStockResolve implements Resolve<IPlayerStock> {
  constructor(private service: PlayerStockService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlayerStock> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((playerStock: HttpResponse<PlayerStock>) => {
          if (playerStock.body) {
            return of(playerStock.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PlayerStock());
  }
}

export const playerStockRoute: Routes = [
  {
    path: '',
    component: PlayerStockComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.playerStock.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PlayerStockDetailComponent,
    resolve: {
      playerStock: PlayerStockResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.playerStock.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PlayerStockUpdateComponent,
    resolve: {
      playerStock: PlayerStockResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.playerStock.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PlayerStockUpdateComponent,
    resolve: {
      playerStock: PlayerStockResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.playerStock.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
