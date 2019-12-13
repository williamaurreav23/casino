import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CasinoSharedModule } from 'app/shared/shared.module';
import { AktieWertHistoryComponent } from './aktie-wert-history.component';
import { AktieWertHistoryDetailComponent } from './aktie-wert-history-detail.component';
import { AktieWertHistoryUpdateComponent } from './aktie-wert-history-update.component';
import { AktieWertHistoryDeletePopupComponent, AktieWertHistoryDeleteDialogComponent } from './aktie-wert-history-delete-dialog.component';
import { aktieWertHistoryRoute, aktieWertHistoryPopupRoute } from './aktie-wert-history.route';

const ENTITY_STATES = [...aktieWertHistoryRoute, ...aktieWertHistoryPopupRoute];

@NgModule({
  imports: [CasinoSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AktieWertHistoryComponent,
    AktieWertHistoryDetailComponent,
    AktieWertHistoryUpdateComponent,
    AktieWertHistoryDeleteDialogComponent,
    AktieWertHistoryDeletePopupComponent
  ],
  entryComponents: [AktieWertHistoryDeleteDialogComponent]
})
export class CasinoAktieWertHistoryModule {}
