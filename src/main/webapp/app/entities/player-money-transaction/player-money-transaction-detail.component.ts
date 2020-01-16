import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlayerMoneyTransaction } from 'app/shared/model/player-money-transaction.model';

@Component({
  selector: 'jhi-player-money-transaction-detail',
  templateUrl: './player-money-transaction-detail.component.html'
})
export class PlayerMoneyTransactionDetailComponent implements OnInit {
  playerMoneyTransaction: IPlayerMoneyTransaction | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ playerMoneyTransaction }) => {
      this.playerMoneyTransaction = playerMoneyTransaction;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
