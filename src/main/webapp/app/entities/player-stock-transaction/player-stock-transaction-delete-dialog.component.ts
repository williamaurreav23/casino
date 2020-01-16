import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlayerStockTransaction } from 'app/shared/model/player-stock-transaction.model';
import { PlayerStockTransactionService } from './player-stock-transaction.service';

@Component({
  templateUrl: './player-stock-transaction-delete-dialog.component.html'
})
export class PlayerStockTransactionDeleteDialogComponent {
  playerStockTransaction?: IPlayerStockTransaction;

  constructor(
    protected playerStockTransactionService: PlayerStockTransactionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.playerStockTransactionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('playerStockTransactionListModification');
      this.activeModal.close();
    });
  }
}
