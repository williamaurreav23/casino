import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAktieWertHistory } from 'app/shared/model/aktie-wert-history.model';

@Component({
  selector: 'jhi-aktie-wert-history-detail',
  templateUrl: './aktie-wert-history-detail.component.html'
})
export class AktieWertHistoryDetailComponent implements OnInit {
  aktieWertHistory: IAktieWertHistory;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ aktieWertHistory }) => {
      this.aktieWertHistory = aktieWertHistory;
    });
  }

  previousState() {
    window.history.back();
  }
}
