import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAktie } from 'app/shared/model/aktie.model';

@Component({
  selector: 'jhi-aktie-detail',
  templateUrl: './aktie-detail.component.html'
})
export class AktieDetailComponent implements OnInit {
  aktie: IAktie;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ aktie }) => {
      this.aktie = aktie;
    });
  }

  previousState() {
    window.history.back();
  }
}
