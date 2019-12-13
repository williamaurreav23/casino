import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IAktieWertHistory } from 'app/shared/model/aktie-wert-history.model';
import { AccountService } from 'app/core/auth/account.service';
import { AktieWertHistoryService } from './aktie-wert-history.service';

@Component({
  selector: 'jhi-aktie-wert-history',
  templateUrl: './aktie-wert-history.component.html'
})
export class AktieWertHistoryComponent implements OnInit, OnDestroy {
  aktieWertHistories: IAktieWertHistory[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected aktieWertHistoryService: AktieWertHistoryService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.aktieWertHistoryService
      .query()
      .pipe(
        filter((res: HttpResponse<IAktieWertHistory[]>) => res.ok),
        map((res: HttpResponse<IAktieWertHistory[]>) => res.body)
      )
      .subscribe((res: IAktieWertHistory[]) => {
        this.aktieWertHistories = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAktieWertHistories();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAktieWertHistory) {
    return item.id;
  }

  registerChangeInAktieWertHistories() {
    this.eventSubscriber = this.eventManager.subscribe('aktieWertHistoryListModification', response => this.loadAll());
  }
}
