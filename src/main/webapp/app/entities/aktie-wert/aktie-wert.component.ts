import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IAktieWert } from 'app/shared/model/aktie-wert.model';
import { AccountService } from 'app/core/auth/account.service';
import { AktieWertService } from './aktie-wert.service';

@Component({
  selector: 'jhi-aktie-wert',
  templateUrl: './aktie-wert.component.html'
})
export class AktieWertComponent implements OnInit, OnDestroy {
  aktieWerts: IAktieWert[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected aktieWertService: AktieWertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.aktieWertService
      .query()
      .pipe(
        filter((res: HttpResponse<IAktieWert[]>) => res.ok),
        map((res: HttpResponse<IAktieWert[]>) => res.body)
      )
      .subscribe((res: IAktieWert[]) => {
        this.aktieWerts = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAktieWerts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAktieWert) {
    return item.id;
  }

  registerChangeInAktieWerts() {
    this.eventSubscriber = this.eventManager.subscribe('aktieWertListModification', response => this.loadAll());
  }
}
