import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'player',
        loadChildren: () => import('./player/player.module').then(m => m.CasinoPlayerModule)
      },
      {
        path: 'stock',
        loadChildren: () => import('./stock/stock.module').then(m => m.CasinoStockModule)
      },
      {
        path: 'game-ended',
        loadChildren: () => import('./game-ended/game-ended.module').then(m => m.CasinoGameEndedModule)
      },
      {
        path: 'player-money-transaction',
        loadChildren: () =>
          import('./player-money-transaction/player-money-transaction.module').then(m => m.CasinoPlayerMoneyTransactionModule)
      },
      {
        path: 'player-stock-transaction',
        loadChildren: () =>
          import('./player-stock-transaction/player-stock-transaction.module').then(m => m.CasinoPlayerStockTransactionModule)
      },
      {
        path: 'player-stock',
        loadChildren: () => import('./player-stock/player-stock.module').then(m => m.CasinoPlayerStockModule)
      },
      {
        path: 'stock-value-change',
        loadChildren: () => import('./stock-value-change/stock-value-change.module').then(m => m.CasinoStockValueChangeModule)
      },
      {
        path: 'user-extra',
        loadChildren: () => import('./user-extra/user-extra.module').then(m => m.CasinoUserExtraModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class CasinoEntityModule {}
