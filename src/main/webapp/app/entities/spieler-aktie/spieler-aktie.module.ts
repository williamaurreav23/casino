import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CasinoSharedModule } from 'app/shared/shared.module';
import { SpielerAktieComponent } from './spieler-aktie.component';
import { SpielerAktieDetailComponent } from './spieler-aktie-detail.component';
import { SpielerAktieUpdateComponent } from './spieler-aktie-update.component';
import { SpielerAktieDeletePopupComponent, SpielerAktieDeleteDialogComponent } from './spieler-aktie-delete-dialog.component';
import { spielerAktieRoute, spielerAktiePopupRoute } from './spieler-aktie.route';

const ENTITY_STATES = [...spielerAktieRoute, ...spielerAktiePopupRoute];

@NgModule({
  imports: [CasinoSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SpielerAktieComponent,
    SpielerAktieDetailComponent,
    SpielerAktieUpdateComponent,
    SpielerAktieDeleteDialogComponent,
    SpielerAktieDeletePopupComponent
  ],
  entryComponents: [SpielerAktieDeleteDialogComponent]
})
export class CasinoSpielerAktieModule {}
