import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CasinoSharedModule } from 'app/shared/shared.module';
import { StockValueChangeComponent } from './stock-value-change.component';
import { StockValueChangeDetailComponent } from './stock-value-change-detail.component';
import { StockValueChangeUpdateComponent } from './stock-value-change-update.component';
import { StockValueChangeDeleteDialogComponent } from './stock-value-change-delete-dialog.component';
import { stockValueChangeRoute } from './stock-value-change.route';

@NgModule({
  imports: [CasinoSharedModule, RouterModule.forChild(stockValueChangeRoute)],
  declarations: [
    StockValueChangeComponent,
    StockValueChangeDetailComponent,
    StockValueChangeUpdateComponent,
    StockValueChangeDeleteDialogComponent
  ],
  entryComponents: [StockValueChangeDeleteDialogComponent]
})
export class CasinoStockValueChangeModule {}
