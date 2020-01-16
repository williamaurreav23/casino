import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlayerMoneyTransaction } from 'app/shared/model/player-money-transaction.model';
import { PlayerMoneyTransactionService } from './player-money-transaction.service';

@Component({
  templateUrl: './player-money-transaction-delete-dialog.component.html'
})
export class PlayerMoneyTransactionDeleteDialogComponent {
  playerMoneyTransaction?: IPlayerMoneyTransaction;

  constructor(
    protected playerMoneyTransactionService: PlayerMoneyTransactionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.playerMoneyTransactionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('playerMoneyTransactionListModification');
      this.activeModal.close();
    });
  }
}
