import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CasinoSharedModule } from 'app/shared/shared.module';
import { AktieComponent } from './aktie.component';
import { AktieDetailComponent } from './aktie-detail.component';
import { AktieUpdateComponent } from './aktie-update.component';
import { AktieDeletePopupComponent, AktieDeleteDialogComponent } from './aktie-delete-dialog.component';
import { aktieRoute, aktiePopupRoute } from './aktie.route';

const ENTITY_STATES = [...aktieRoute, ...aktiePopupRoute];

@NgModule({
  imports: [CasinoSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [AktieComponent, AktieDetailComponent, AktieUpdateComponent, AktieDeleteDialogComponent, AktieDeletePopupComponent],
  entryComponents: [AktieDeleteDialogComponent]
})
export class CasinoAktieModule {}
