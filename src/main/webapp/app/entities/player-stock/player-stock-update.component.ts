import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IPlayerStock, PlayerStock } from 'app/shared/model/player-stock.model';
import { PlayerStockService } from './player-stock.service';
import { IPlayer } from 'app/shared/model/player.model';
import { PlayerService } from 'app/entities/player/player.service';
import { IStock } from 'app/shared/model/stock.model';
import { StockService } from 'app/entities/stock/stock.service';

type SelectableEntity = IPlayer | IStock;

@Component({
  selector: 'jhi-player-stock-update',
  templateUrl: './player-stock-update.component.html'
})
export class PlayerStockUpdateComponent implements OnInit {
  isSaving = false;

  players: IPlayer[] = [];

  stocks: IStock[] = [];

  editForm = this.fb.group({
    id: [],
    amount: [],
    playerId: [],
    stockId: []
  });

  constructor(
    protected playerStockService: PlayerStockService,
    protected playerService: PlayerService,
    protected stockService: StockService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ playerStock }) => {
      this.updateForm(playerStock);

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

  updateForm(playerStock: IPlayerStock): void {
    this.editForm.patchValue({
      id: playerStock.id,
      amount: playerStock.amount,
      playerId: playerStock.playerId,
      stockId: playerStock.stockId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const playerStock = this.createFromForm();
    if (playerStock.id !== undefined) {
      this.subscribeToSaveResponse(this.playerStockService.update(playerStock));
    } else {
      this.subscribeToSaveResponse(this.playerStockService.create(playerStock));
    }
  }

  private createFromForm(): IPlayerStock {
    return {
      ...new PlayerStock(),
      id: this.editForm.get(['id'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      playerId: this.editForm.get(['playerId'])!.value,
      stockId: this.editForm.get(['stockId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlayerStock>>): void {
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
