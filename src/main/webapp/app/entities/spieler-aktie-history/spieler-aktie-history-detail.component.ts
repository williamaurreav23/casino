import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISpielerAktieHistory } from 'app/shared/model/spieler-aktie-history.model';

@Component({
  selector: 'jhi-spieler-aktie-history-detail',
  templateUrl: './spieler-aktie-history-detail.component.html'
})
export class SpielerAktieHistoryDetailComponent implements OnInit {
  spielerAktieHistory: ISpielerAktieHistory;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ spielerAktieHistory }) => {
      this.spielerAktieHistory = spielerAktieHistory;
    });
  }

  previousState() {
    window.history.back();
  }
}
