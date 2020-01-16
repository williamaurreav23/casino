import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlayerStock } from 'app/shared/model/player-stock.model';

@Component({
  selector: 'jhi-player-stock-detail',
  templateUrl: './player-stock-detail.component.html'
})
export class PlayerStockDetailComponent implements OnInit {
  playerStock: IPlayerStock | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ playerStock }) => {
      this.playerStock = playerStock;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
