import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISpielerTransaktion } from 'app/shared/model/spieler-transaktion.model';

@Component({
  selector: 'jhi-spieler-transaktion-detail',
  templateUrl: './spieler-transaktion-detail.component.html'
})
export class SpielerTransaktionDetailComponent implements OnInit {
  spielerTransaktion: ISpielerTransaktion;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ spielerTransaktion }) => {
      this.spielerTransaktion = spielerTransaktion;
    });
  }

  previousState() {
    window.history.back();
  }
}
