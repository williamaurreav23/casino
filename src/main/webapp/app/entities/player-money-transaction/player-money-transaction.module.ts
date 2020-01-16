import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CasinoSharedModule } from 'app/shared/shared.module';
import { PlayerMoneyTransactionComponent } from './player-money-transaction.component';
import { PlayerMoneyTransactionDetailComponent } from './player-money-transaction-detail.component';
import { PlayerMoneyTransactionUpdateComponent } from './player-money-transaction-update.component';
import { PlayerMoneyTransactionDeleteDialogComponent } from './player-money-transaction-delete-dialog.component';
import { playerMoneyTransactionRoute } from './player-money-transaction.route';

@NgModule({
  imports: [CasinoSharedModule, RouterModule.forChild(playerMoneyTransactionRoute)],
  declarations: [
    PlayerMoneyTransactionComponent,
    PlayerMoneyTransactionDetailComponent,
    PlayerMoneyTransactionUpdateComponent,
    PlayerMoneyTransactionDeleteDialogComponent
  ],
  entryComponents: [PlayerMoneyTransactionDeleteDialogComponent]
})
export class CasinoPlayerMoneyTransactionModule {}
