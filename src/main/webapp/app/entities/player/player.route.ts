import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPlayer, Player } from 'app/shared/model/player.model';
import { PlayerService } from './player.service';
import { PlayerComponent } from './player.component';
import { PlayerDetailComponent } from './player-detail.component';
import { PlayerUpdateComponent } from './player-update.component';

@Injectable({ providedIn: 'root' })
export class PlayerResolve implements Resolve<IPlayer> {
  constructor(private service: PlayerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlayer> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((player: HttpResponse<Player>) => {
          if (player.body) {
            return of(player.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Player());
  }
}

export const playerRoute: Routes = [
  {
    path: '',
    component: PlayerComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.player.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PlayerDetailComponent,
    resolve: {
      player: PlayerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.player.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PlayerUpdateComponent,
    resolve: {
      player: PlayerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.player.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PlayerUpdateComponent,
    resolve: {
      player: PlayerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.player.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
