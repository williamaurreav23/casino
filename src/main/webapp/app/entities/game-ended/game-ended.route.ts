import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGameEnded, GameEnded } from 'app/shared/model/game-ended.model';
import { GameEndedService } from './game-ended.service';
import { GameEndedComponent } from './game-ended.component';
import { GameEndedDetailComponent } from './game-ended-detail.component';
import { GameEndedUpdateComponent } from './game-ended-update.component';

@Injectable({ providedIn: 'root' })
export class GameEndedResolve implements Resolve<IGameEnded> {
  constructor(private service: GameEndedService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGameEnded> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((gameEnded: HttpResponse<GameEnded>) => {
          if (gameEnded.body) {
            return of(gameEnded.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GameEnded());
  }
}

export const gameEndedRoute: Routes = [
  {
    path: '',
    component: GameEndedComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.gameEnded.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GameEndedDetailComponent,
    resolve: {
      gameEnded: GameEndedResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.gameEnded.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GameEndedUpdateComponent,
    resolve: {
      gameEnded: GameEndedResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.gameEnded.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GameEndedUpdateComponent,
    resolve: {
      gameEnded: GameEndedResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.gameEnded.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
