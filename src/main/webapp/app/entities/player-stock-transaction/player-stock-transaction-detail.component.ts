import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlayerStockTransaction } from 'app/shared/model/player-stock-transaction.model';

@Component({
  selector: 'jhi-player-stock-transaction-detail',
  templateUrl: './player-stock-transaction-detail.component.html'
})
export class PlayerStockTransactionDetailComponent implements OnInit {
  playerStockTransaction: IPlayerStockTransaction | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ playerStockTransaction }) => {
      this.playerStockTransaction = playerStockTransaction;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
