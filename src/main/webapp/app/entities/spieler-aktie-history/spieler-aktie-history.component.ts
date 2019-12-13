import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ISpielerAktieHistory } from 'app/shared/model/spieler-aktie-history.model';
import { AccountService } from 'app/core/auth/account.service';
import { SpielerAktieHistoryService } from './spieler-aktie-history.service';

@Component({
  selector: 'jhi-spieler-aktie-history',
  templateUrl: './spieler-aktie-history.component.html'
})
export class SpielerAktieHistoryComponent implements OnInit, OnDestroy {
  spielerAktieHistories: ISpielerAktieHistory[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected spielerAktieHistoryService: SpielerAktieHistoryService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.spielerAktieHistoryService
      .query()
      .pipe(
        filter((res: HttpResponse<ISpielerAktieHistory[]>) => res.ok),
        map((res: HttpResponse<ISpielerAktieHistory[]>) => res.body)
      )
      .subscribe((res: ISpielerAktieHistory[]) => {
        this.spielerAktieHistories = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSpielerAktieHistories();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISpielerAktieHistory) {
    return item.id;
  }

  registerChangeInSpielerAktieHistories() {
    this.eventSubscriber = this.eventManager.subscribe('spielerAktieHistoryListModification', response => this.loadAll());
  }
}
