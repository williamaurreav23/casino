import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { ISpielerAktie } from 'app/shared/model/spieler-aktie.model';
import { AccountService } from 'app/core/auth/account.service';
import { SpielerAktieService } from './spieler-aktie.service';

@Component({
  selector: 'jhi-spieler-aktie',
  templateUrl: './spieler-aktie.component.html'
})
export class SpielerAktieComponent implements OnInit, OnDestroy {
  spielerAkties: ISpielerAktie[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected spielerAktieService: SpielerAktieService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.spielerAktieService
      .query()
      .pipe(
        filter((res: HttpResponse<ISpielerAktie[]>) => res.ok),
        map((res: HttpResponse<ISpielerAktie[]>) => res.body)
      )
      .subscribe((res: ISpielerAktie[]) => {
        this.spielerAkties = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSpielerAkties();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISpielerAktie) {
    return item.id;
  }

  registerChangeInSpielerAkties() {
    this.eventSubscriber = this.eventManager.subscribe('spielerAktieListModification', response => this.loadAll());
  }
}
