import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlayerStock } from 'app/shared/model/player-stock.model';
import { PlayerStockService } from './player-stock.service';

@Component({
  templateUrl: './player-stock-delete-dialog.component.html'
})
export class PlayerStockDeleteDialogComponent {
  playerStock?: IPlayerStock;

  constructor(
    protected playerStockService: PlayerStockService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.playerStockService.delete(id).subscribe(() => {
      this.eventManager.broadcast('playerStockListModification');
      this.activeModal.close();
    });
  }
}
