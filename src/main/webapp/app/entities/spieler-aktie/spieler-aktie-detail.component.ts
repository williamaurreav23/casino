import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISpielerAktie } from 'app/shared/model/spieler-aktie.model';

@Component({
  selector: 'jhi-spieler-aktie-detail',
  templateUrl: './spieler-aktie-detail.component.html'
})
export class SpielerAktieDetailComponent implements OnInit {
  spielerAktie: ISpielerAktie;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ spielerAktie }) => {
      this.spielerAktie = spielerAktie;
    });
  }

  previousState() {
    window.history.back();
  }
}
