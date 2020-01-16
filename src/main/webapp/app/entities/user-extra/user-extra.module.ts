import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CasinoSharedModule } from 'app/shared/shared.module';
import { UserExtraComponent } from './user-extra.component';
import { UserExtraDetailComponent } from './user-extra-detail.component';
import { UserExtraUpdateComponent } from './user-extra-update.component';
import { UserExtraDeleteDialogComponent } from './user-extra-delete-dialog.component';
import { userExtraRoute } from './user-extra.route';

@NgModule({
  imports: [CasinoSharedModule, RouterModule.forChild(userExtraRoute)],
  declarations: [UserExtraComponent, UserExtraDetailComponent, UserExtraUpdateComponent, UserExtraDeleteDialogComponent],
  entryComponents: [UserExtraDeleteDialogComponent]
})
export class CasinoUserExtraModule {}
