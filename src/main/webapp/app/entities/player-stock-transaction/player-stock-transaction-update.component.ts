import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPlayerStockTransaction, PlayerStockTransaction } from 'app/shared/model/player-stock-transaction.model';
import { PlayerStockTransactionService } from './player-stock-transaction.service';
import { IPlayer } from 'app/shared/model/player.model';
import { PlayerService } from 'app/entities/player/player.service';
import { IStock } from 'app/shared/model/stock.model';
import { StockService } from 'app/entities/stock/stock.service';

type SelectableEntity = IPlayer | IStock;

@Component({
  selector: 'jhi-player-stock-transaction-update',
  templateUrl: './player-stock-transaction-update.component.html'
})
export class PlayerStockTransactionUpdateComponent implements OnInit {
  isSaving = false;

  players: IPlayer[] = [];

  stocks: IStock[] = [];

  editForm = this.fb.group({
    id: [],
    time: [],
    amount: [],
    playerId: [],
    stockId: []
  });

  constructor(
    protected playerStockTransactionService: PlayerStockTransactionService,
    protected playerService: PlayerService,
    protected stockService: StockService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ playerStockTransaction }) => {
      this.updateForm(playerStockTransaction);

      this.playerService
        .query()
        .pipe(
          map((res: HttpResponse<IPlayer[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPlayer[]) => (this.players = resBody));

      this.stockService
        .query()
        .pipe(
          map((res: HttpResponse<IStock[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IStock[]) => (this.stocks = resBody));
    });
  }

  updateForm(playerStockTransaction: IPlayerStockTransaction): void {
    this.editForm.patchValue({
      id: playerStockTransaction.id,
      time: playerStockTransaction.time != null ? playerStockTransaction.time.format(DATE_TIME_FORMAT) : null,
      amount: playerStockTransaction.amount,
      playerId: playerStockTransaction.playerId,
      stockId: playerStockTransaction.stockId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const playerStockTransaction = this.createFromForm();
    if (playerStockTransaction.id !== undefined) {
      this.subscribeToSaveResponse(this.playerStockTransactionService.update(playerStockTransaction));
    } else {
      this.subscribeToSaveResponse(this.playerStockTransactionService.create(playerStockTransaction));
    }
  }

  private createFromForm(): IPlayerStockTransaction {
    return {
      ...new PlayerStockTransaction(),
      id: this.editForm.get(['id'])!.value,
      time: this.editForm.get(['time'])!.value != null ? moment(this.editForm.get(['time'])!.value, DATE_TIME_FORMAT) : undefined,
      amount: this.editForm.get(['amount'])!.value,
      playerId: this.editForm.get(['playerId'])!.value,
      stockId: this.editForm.get(['stockId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlayerStockTransaction>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
