import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGameEnded } from 'app/shared/model/game-ended.model';
import { GameEndedService } from './game-ended.service';
import { GameEndedDeleteDialogComponent } from './game-ended-delete-dialog.component';

@Component({
  selector: 'jhi-game-ended',
  templateUrl: './game-ended.component.html'
})
export class GameEndedComponent implements OnInit, OnDestroy {
  gameEndeds?: IGameEnded[];
  eventSubscriber?: Subscription;

  constructor(protected gameEndedService: GameEndedService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.gameEndedService.query().subscribe((res: HttpResponse<IGameEnded[]>) => {
      this.gameEndeds = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGameEndeds();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGameEnded): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGameEndeds(): void {
    this.eventSubscriber = this.eventManager.subscribe('gameEndedListModification', () => this.loadAll());
  }

  delete(gameEnded: IGameEnded): void {
    const modalRef = this.modalService.open(GameEndedDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.gameEnded = gameEnded;
  }
}
