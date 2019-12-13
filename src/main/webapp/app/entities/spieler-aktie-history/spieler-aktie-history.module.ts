import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CasinoSharedModule } from 'app/shared/shared.module';
import { SpielerAktieHistoryComponent } from './spieler-aktie-history.component';
import { SpielerAktieHistoryDetailComponent } from './spieler-aktie-history-detail.component';
import { SpielerAktieHistoryUpdateComponent } from './spieler-aktie-history-update.component';
import {
  SpielerAktieHistoryDeletePopupComponent,
  SpielerAktieHistoryDeleteDialogComponent
} from './spieler-aktie-history-delete-dialog.component';
import { spielerAktieHistoryRoute, spielerAktieHistoryPopupRoute } from './spieler-aktie-history.route';

const ENTITY_STATES = [...spielerAktieHistoryRoute, ...spielerAktieHistoryPopupRoute];

@NgModule({
  imports: [CasinoSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SpielerAktieHistoryComponent,
    SpielerAktieHistoryDetailComponent,
    SpielerAktieHistoryUpdateComponent,
    SpielerAktieHistoryDeleteDialogComponent,
    SpielerAktieHistoryDeletePopupComponent
  ],
  entryComponents: [SpielerAktieHistoryDeleteDialogComponent]
})
export class CasinoSpielerAktieHistoryModule {}
