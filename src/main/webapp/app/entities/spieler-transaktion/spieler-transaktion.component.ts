import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ISpielerTransaktion } from 'app/shared/model/spieler-transaktion.model';
import { AccountService } from 'app/core/auth/account.service';
import { SpielerTransaktionService } from './spieler-transaktion.service';

@Component({
  selector: 'jhi-spieler-transaktion',
  templateUrl: './spieler-transaktion.component.html'
})
export class SpielerTransaktionComponent implements OnInit, OnDestroy {
  spielerTransaktions: ISpielerTransaktion[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected spielerTransaktionService: SpielerTransaktionService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.spielerTransaktionService
      .query()
      .pipe(
        filter((res: HttpResponse<ISpielerTransaktion[]>) => res.ok),
        map((res: HttpResponse<ISpielerTransaktion[]>) => res.body)
      )
      .subscribe((res: ISpielerTransaktion[]) => {
        this.spielerTransaktions = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSpielerTransaktions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISpielerTransaktion) {
    return item.id;
  }

  registerChangeInSpielerTransaktions() {
    this.eventSubscriber = this.eventManager.subscribe('spielerTransaktionListModification', response => this.loadAll());
  }
}
