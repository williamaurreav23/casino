import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlayerMoneyTransaction, PlayerMoneyTransaction } from 'app/shared/model/player-money-transaction.model';
import { PlayerMoneyTransactionService } from './player-money-transaction.service';
import { PlayerMoneyTransactionComponent } from './player-money-transaction.component';
import { PlayerMoneyTransactionDetailComponent } from './player-money-transaction-detail.component';
import { PlayerMoneyTransactionUpdateComponent } from './player-money-transaction-update.component';

@Injectable({ providedIn: 'root' })
export class PlayerMoneyTransactionResolve implements Resolve<IPlayerMoneyTransaction> {
  constructor(private service: PlayerMoneyTransactionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlayerMoneyTransaction> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((playerMoneyTransaction: HttpResponse<PlayerMoneyTransaction>) => {
          if (playerMoneyTransaction.body) {
            return of(playerMoneyTransaction.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PlayerMoneyTransaction());
  }
}

export const playerMoneyTransactionRoute: Routes = [
  {
    path: '',
    component: PlayerMoneyTransactionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.playerMoneyTransaction.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PlayerMoneyTransactionDetailComponent,
    resolve: {
      playerMoneyTransaction: PlayerMoneyTransactionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.playerMoneyTransaction.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PlayerMoneyTransactionUpdateComponent,
    resolve: {
      playerMoneyTransaction: PlayerMoneyTransactionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.playerMoneyTransaction.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PlayerMoneyTransactionUpdateComponent,
    resolve: {
      playerMoneyTransaction: PlayerMoneyTransactionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.playerMoneyTransaction.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
