import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CasinoSharedModule } from 'app/shared/shared.module';
import { SpielerTransaktionComponent } from './spieler-transaktion.component';
import { SpielerTransaktionDetailComponent } from './spieler-transaktion-detail.component';
import { SpielerTransaktionUpdateComponent } from './spieler-transaktion-update.component';
import {
  SpielerTransaktionDeletePopupComponent,
  SpielerTransaktionDeleteDialogComponent
} from './spieler-transaktion-delete-dialog.component';
import { spielerTransaktionRoute, spielerTransaktionPopupRoute } from './spieler-transaktion.route';

const ENTITY_STATES = [...spielerTransaktionRoute, ...spielerTransaktionPopupRoute];

@NgModule({
  imports: [CasinoSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SpielerTransaktionComponent,
    SpielerTransaktionDetailComponent,
    SpielerTransaktionUpdateComponent,
    SpielerTransaktionDeleteDialogComponent,
    SpielerTransaktionDeletePopupComponent
  ],
  entryComponents: [SpielerTransaktionDeleteDialogComponent]
})
export class CasinoSpielerTransaktionModule {}
