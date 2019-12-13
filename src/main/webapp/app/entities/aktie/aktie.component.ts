import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IAktie } from 'app/shared/model/aktie.model';
import { AccountService } from 'app/core/auth/account.service';
import { AktieService } from './aktie.service';

@Component({
  selector: 'jhi-aktie',
  templateUrl: './aktie.component.html'
})
export class AktieComponent implements OnInit, OnDestroy {
  akties: IAktie[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(protected aktieService: AktieService, protected eventManager: JhiEventManager, protected accountService: AccountService) {}

  loadAll() {
    this.aktieService
      .query()
      .pipe(
        filter((res: HttpResponse<IAktie[]>) => res.ok),
        map((res: HttpResponse<IAktie[]>) => res.body)
      )
      .subscribe((res: IAktie[]) => {
        this.akties = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAkties();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAktie) {
    return item.id;
  }

  registerChangeInAkties() {
    this.eventSubscriber = this.eventManager.subscribe('aktieListModification', response => this.loadAll());
  }
}
