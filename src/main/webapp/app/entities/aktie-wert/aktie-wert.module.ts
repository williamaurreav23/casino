import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CasinoSharedModule } from 'app/shared/shared.module';
import { AktieWertComponent } from './aktie-wert.component';
import { AktieWertDetailComponent } from './aktie-wert-detail.component';
import { AktieWertUpdateComponent } from './aktie-wert-update.component';
import { AktieWertDeletePopupComponent, AktieWertDeleteDialogComponent } from './aktie-wert-delete-dialog.component';
import { aktieWertRoute, aktieWertPopupRoute } from './aktie-wert.route';

const ENTITY_STATES = [...aktieWertRoute, ...aktieWertPopupRoute];

@NgModule({
  imports: [CasinoSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AktieWertComponent,
    AktieWertDetailComponent,
    AktieWertUpdateComponent,
    AktieWertDeleteDialogComponent,
    AktieWertDeletePopupComponent
  ],
  entryComponents: [AktieWertDeleteDialogComponent]
})
export class CasinoAktieWertModule {}
