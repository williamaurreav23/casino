import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserExtra, UserExtra } from 'app/shared/model/user-extra.model';
import { UserExtraService } from './user-extra.service';
import { UserExtraComponent } from './user-extra.component';
import { UserExtraDetailComponent } from './user-extra-detail.component';
import { UserExtraUpdateComponent } from './user-extra-update.component';

@Injectable({ providedIn: 'root' })
export class UserExtraResolve implements Resolve<IUserExtra> {
  constructor(private service: UserExtraService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserExtra> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userExtra: HttpResponse<UserExtra>) => {
          if (userExtra.body) {
            return of(userExtra.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserExtra());
  }
}

export const userExtraRoute: Routes = [
  {
    path: '',
    component: UserExtraComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.userExtra.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UserExtraDetailComponent,
    resolve: {
      userExtra: UserExtraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.userExtra.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UserExtraUpdateComponent,
    resolve: {
      userExtra: UserExtraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.userExtra.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UserExtraUpdateComponent,
    resolve: {
      userExtra: UserExtraResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.userExtra.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
