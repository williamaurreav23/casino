import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPlayerStock } from 'app/shared/model/player-stock.model';
import { PlayerStockService } from './player-stock.service';
import { PlayerStockDeleteDialogComponent } from './player-stock-delete-dialog.component';

@Component({
  selector: 'jhi-player-stock',
  templateUrl: './player-stock.component.html'
})
export class PlayerStockComponent implements OnInit, OnDestroy {
  playerStocks?: IPlayerStock[];
  eventSubscriber?: Subscription;

  constructor(
    protected playerStockService: PlayerStockService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.playerStockService.query().subscribe((res: HttpResponse<IPlayerStock[]>) => {
      this.playerStocks = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPlayerStocks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPlayerStock): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPlayerStocks(): void {
    this.eventSubscriber = this.eventManager.subscribe('playerStockListModification', () => this.loadAll());
  }

  delete(playerStock: IPlayerStock): void {
    const modalRef = this.modalService.open(PlayerStockDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.playerStock = playerStock;
  }
}
