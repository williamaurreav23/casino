import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStockValueChange, StockValueChange } from 'app/shared/model/stock-value-change.model';
import { StockValueChangeService } from './stock-value-change.service';
import { StockValueChangeComponent } from './stock-value-change.component';
import { StockValueChangeDetailComponent } from './stock-value-change-detail.component';
import { StockValueChangeUpdateComponent } from './stock-value-change-update.component';

@Injectable({ providedIn: 'root' })
export class StockValueChangeResolve implements Resolve<IStockValueChange> {
  constructor(private service: StockValueChangeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStockValueChange> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((stockValueChange: HttpResponse<StockValueChange>) => {
          if (stockValueChange.body) {
            return of(stockValueChange.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new StockValueChange());
  }
}

export const stockValueChangeRoute: Routes = [
  {
    path: '',
    component: StockValueChangeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.stockValueChange.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StockValueChangeDetailComponent,
    resolve: {
      stockValueChange: StockValueChangeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.stockValueChange.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StockValueChangeUpdateComponent,
    resolve: {
      stockValueChange: StockValueChangeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.stockValueChange.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StockValueChangeUpdateComponent,
    resolve: {
      stockValueChange: StockValueChangeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.stockValueChange.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
