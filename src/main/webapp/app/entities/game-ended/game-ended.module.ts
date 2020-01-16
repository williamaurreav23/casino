import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CasinoSharedModule } from 'app/shared/shared.module';
import { GameEndedComponent } from './game-ended.component';
import { GameEndedDetailComponent } from './game-ended-detail.component';
import { GameEndedUpdateComponent } from './game-ended-update.component';
import { GameEndedDeleteDialogComponent } from './game-ended-delete-dialog.component';
import { gameEndedRoute } from './game-ended.route';

@NgModule({
  imports: [CasinoSharedModule, RouterModule.forChild(gameEndedRoute)],
  declarations: [GameEndedComponent, GameEndedDetailComponent, GameEndedUpdateComponent, GameEndedDeleteDialogComponent],
  entryComponents: [GameEndedDeleteDialogComponent]
})
export class CasinoGameEndedModule {}
