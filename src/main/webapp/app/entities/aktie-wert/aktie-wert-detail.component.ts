import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAktieWert } from 'app/shared/model/aktie-wert.model';

@Component({
  selector: 'jhi-aktie-wert-detail',
  templateUrl: './aktie-wert-detail.component.html'
})
export class AktieWertDetailComponent implements OnInit {
  aktieWert: IAktieWert;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ aktieWert }) => {
      this.aktieWert = aktieWert;
    });
  }

  previousState() {
    window.history.back();
  }
}
