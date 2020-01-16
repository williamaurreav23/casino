import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGameEnded } from 'app/shared/model/game-ended.model';
import { GameEndedService } from './game-ended.service';

@Component({
  templateUrl: './game-ended-delete-dialog.component.html'
})
export class GameEndedDeleteDialogComponent {
  gameEnded?: IGameEnded;

  constructor(protected gameEndedService: GameEndedService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.gameEndedService.delete(id).subscribe(() => {
      this.eventManager.broadcast('gameEndedListModification');
      this.activeModal.close();
    });
  }
}
