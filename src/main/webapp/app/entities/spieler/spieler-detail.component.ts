import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISpieler } from 'app/shared/model/spieler.model';

@Component({
  selector: 'jhi-spieler-detail',
  templateUrl: './spieler-detail.component.html'
})
export class SpielerDetailComponent implements OnInit {
  spieler: ISpieler;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ spieler }) => {
      this.spieler = spieler;
    });
  }

  previousState() {
    window.history.back();
  }
}
