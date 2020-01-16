import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserExtra } from 'app/shared/model/user-extra.model';
import { UserExtraService } from './user-extra.service';

@Component({
  templateUrl: './user-extra-delete-dialog.component.html'
})
export class UserExtraDeleteDialogComponent {
  userExtra?: IUserExtra;

  constructor(protected userExtraService: UserExtraService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userExtraService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userExtraListModification');
      this.activeModal.close();
    });
  }
}
