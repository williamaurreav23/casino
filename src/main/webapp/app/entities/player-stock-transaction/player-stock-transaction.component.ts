import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlayerStockTransaction } from 'app/shared/model/player-stock-transaction.model';
import { PlayerStockTransactionService } from './player-stock-transaction.service';
import { PlayerStockTransactionDeleteDialogComponent } from './player-stock-transaction-delete-dialog.component';

@Component({
  selector: 'jhi-player-stock-transaction',
  templateUrl: './player-stock-transaction.component.html'
})
export class PlayerStockTransactionComponent implements OnInit, OnDestroy {
  playerStockTransactions?: IPlayerStockTransaction[];
  eventSubscriber?: Subscription;

  constructor(
    protected playerStockTransactionService: PlayerStockTransactionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.playerStockTransactionService.query().subscribe((res: HttpResponse<IPlayerStockTransaction[]>) => {
      this.playerStockTransactions = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPlayerStockTransactions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPlayerStockTransaction): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPlayerStockTransactions(): void {
    this.eventSubscriber = this.eventManager.subscribe('playerStockTransactionListModification', () => this.loadAll());
  }

  delete(playerStockTransaction: IPlayerStockTransaction): void {
    const modalRef = this.modalService.open(PlayerStockTransactionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.playerStockTransaction = playerStockTransaction;
  }
}
