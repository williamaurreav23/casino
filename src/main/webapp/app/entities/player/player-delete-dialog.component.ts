import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlayer } from 'app/shared/model/player.model';
import { PlayerService } from './player.service';

@Component({
  templateUrl: './player-delete-dialog.component.html'
})
export class PlayerDeleteDialogComponent {
  player?: IPlayer;

  constructor(protected playerService: PlayerService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.playerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('playerListModification');
      this.activeModal.close();
    });
  }
}
