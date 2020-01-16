import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlayerMoneyTransaction } from 'app/shared/model/player-money-transaction.model';
import { PlayerMoneyTransactionService } from './player-money-transaction.service';
import { PlayerMoneyTransactionDeleteDialogComponent } from './player-money-transaction-delete-dialog.component';

@Component({
  selector: 'jhi-player-money-transaction',
  templateUrl: './player-money-transaction.component.html'
})
export class PlayerMoneyTransactionComponent implements OnInit, OnDestroy {
  playerMoneyTransactions?: IPlayerMoneyTransaction[];
  eventSubscriber?: Subscription;

  constructor(
    protected playerMoneyTransactionService: PlayerMoneyTransactionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.playerMoneyTransactionService.query().subscribe((res: HttpResponse<IPlayerMoneyTransaction[]>) => {
      this.playerMoneyTransactions = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPlayerMoneyTransactions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPlayerMoneyTransaction): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPlayerMoneyTransactions(): void {
    this.eventSubscriber = this.eventManager.subscribe('playerMoneyTransactionListModification', () => this.loadAll());
  }

  delete(playerMoneyTransaction: IPlayerMoneyTransaction): void {
    const modalRef = this.modalService.open(PlayerMoneyTransactionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.playerMoneyTransaction = playerMoneyTransaction;
  }
}
