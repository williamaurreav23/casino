import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CasinoSharedModule } from 'app/shared/shared.module';
import { PlayerStockTransactionComponent } from './player-stock-transaction.component';
import { PlayerStockTransactionDetailComponent } from './player-stock-transaction-detail.component';
import { PlayerStockTransactionUpdateComponent } from './player-stock-transaction-update.component';
import { PlayerStockTransactionDeleteDialogComponent } from './player-stock-transaction-delete-dialog.component';
import { playerStockTransactionRoute } from './player-stock-transaction.route';

@NgModule({
  imports: [CasinoSharedModule, RouterModule.forChild(playerStockTransactionRoute)],
  declarations: [
    PlayerStockTransactionComponent,
    PlayerStockTransactionDetailComponent,
    PlayerStockTransactionUpdateComponent,
    PlayerStockTransactionDeleteDialogComponent
  ],
  entryComponents: [PlayerStockTransactionDeleteDialogComponent]
})
export class CasinoPlayerStockTransactionModule {}
