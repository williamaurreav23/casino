import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CasinoSharedModule } from 'app/shared/shared.module';
import { PlayerStockComponent } from './player-stock.component';
import { PlayerStockDetailComponent } from './player-stock-detail.component';
import { PlayerStockUpdateComponent } from './player-stock-update.component';
import { PlayerStockDeleteDialogComponent } from './player-stock-delete-dialog.component';
import { playerStockRoute } from './player-stock.route';

@NgModule({
  imports: [CasinoSharedModule, RouterModule.forChild(playerStockRoute)],
  declarations: [PlayerStockComponent, PlayerStockDetailComponent, PlayerStockUpdateComponent, PlayerStockDeleteDialogComponent],
  entryComponents: [PlayerStockDeleteDialogComponent]
})
export class CasinoPlayerStockModule {}
