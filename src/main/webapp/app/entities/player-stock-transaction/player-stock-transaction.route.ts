import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlayerStockTransaction, PlayerStockTransaction } from 'app/shared/model/player-stock-transaction.model';
import { PlayerStockTransactionService } from './player-stock-transaction.service';
import { PlayerStockTransactionComponent } from './player-stock-transaction.component';
import { PlayerStockTransactionDetailComponent } from './player-stock-transaction-detail.component';
import { PlayerStockTransactionUpdateComponent } from './player-stock-transaction-update.component';

@Injectable({ providedIn: 'root' })
export class PlayerStockTransactionResolve implements Resolve<IPlayerStockTransaction> {
  constructor(private service: PlayerStockTransactionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlayerStockTransaction> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((playerStockTransaction: HttpResponse<PlayerStockTransaction>) => {
          if (playerStockTransaction.body) {
            return of(playerStockTransaction.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PlayerStockTransaction());
  }
}

export const playerStockTransactionRoute: Routes = [
  {
    path: '',
    component: PlayerStockTransactionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.playerStockTransaction.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PlayerStockTransactionDetailComponent,
    resolve: {
      playerStockTransaction: PlayerStockTransactionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.playerStockTransaction.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PlayerStockTransactionUpdateComponent,
    resolve: {
      playerStockTransaction: PlayerStockTransactionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.playerStockTransaction.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PlayerStockTransactionUpdateComponent,
    resolve: {
      playerStockTransaction: PlayerStockTransactionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.playerStockTransaction.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
