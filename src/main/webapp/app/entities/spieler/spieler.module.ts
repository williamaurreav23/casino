import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CasinoSharedModule } from 'app/shared/shared.module';
import { SpielerComponent } from './spieler.component';
import { SpielerDetailComponent } from './spieler-detail.component';
import { SpielerUpdateComponent } from './spieler-update.component';
import { SpielerDeletePopupComponent, SpielerDeleteDialogComponent } from './spieler-delete-dialog.component';
import { spielerRoute, spielerPopupRoute } from './spieler.route';

const ENTITY_STATES = [...spielerRoute, ...spielerPopupRoute];

@NgModule({
  imports: [CasinoSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SpielerComponent,
    SpielerDetailComponent,
    SpielerUpdateComponent,
    SpielerDeleteDialogComponent,
    SpielerDeletePopupComponent
  ],
  entryComponents: [SpielerDeleteDialogComponent]
})
export class CasinoSpielerModule {}
