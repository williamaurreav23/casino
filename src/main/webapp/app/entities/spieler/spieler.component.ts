import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ISpieler } from 'app/shared/model/spieler.model';
import { AccountService } from 'app/core/auth/account.service';
import { SpielerService } from './spieler.service';

@Component({
  selector: 'jhi-spieler',
  templateUrl: './spieler.component.html'
})
export class SpielerComponent implements OnInit, OnDestroy {
  spielers: ISpieler[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected spielerService: SpielerService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.spielerService
      .query()
      .pipe(
        filter((res: HttpResponse<ISpieler[]>) => res.ok),
        map((res: HttpResponse<ISpieler[]>) => res.body)
      )
      .subscribe((res: ISpieler[]) => {
        this.spielers = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSpielers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISpieler) {
    return item.id;
  }

  registerChangeInSpielers() {
    this.eventSubscriber = this.eventManager.subscribe('spielerListModification', response => this.loadAll());
  }
}
