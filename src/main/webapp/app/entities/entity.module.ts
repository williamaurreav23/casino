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
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class CasinoEntityModule {}
