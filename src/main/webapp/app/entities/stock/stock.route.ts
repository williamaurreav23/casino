import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStock, Stock } from 'app/shared/model/stock.model';
import { StockService } from './stock.service';
import { StockComponent } from './stock.component';
import { StockDetailComponent } from './stock-detail.component';
import { StockUpdateComponent } from './stock-update.component';

@Injectable({ providedIn: 'root' })
export class StockResolve implements Resolve<IStock> {
  constructor(private service: StockService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStock> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((stock: HttpResponse<Stock>) => {
          if (stock.body) {
            return of(stock.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Stock());
  }
}

export const stockRoute: Routes = [
  {
    path: '',
    component: StockComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.stock.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StockDetailComponent,
    resolve: {
      stock: StockResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.stock.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StockUpdateComponent,
    resolve: {
      stock: StockResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.stock.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StockUpdateComponent,
    resolve: {
      stock: StockResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casinoApp.stock.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
