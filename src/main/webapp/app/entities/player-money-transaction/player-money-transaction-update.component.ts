import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPlayerMoneyTransaction, PlayerMoneyTransaction } from 'app/shared/model/player-money-transaction.model';
import { PlayerMoneyTransactionService } from './player-money-transaction.service';
import { IPlayer } from 'app/shared/model/player.model';
import { PlayerService } from 'app/entities/player/player.service';

@Component({
  selector: 'jhi-player-money-transaction-update',
  templateUrl: './player-money-transaction-update.component.html'
})
export class PlayerMoneyTransactionUpdateComponent implements OnInit {
  isSaving = false;

  players: IPlayer[] = [];

  editForm = this.fb.group({
    id: [],
    time: [],
    transaction: [],
    value: [],
    playerId: []
  });

  constructor(
    protected playerMoneyTransactionService: PlayerMoneyTransactionService,
    protected playerService: PlayerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ playerMoneyTransaction }) => {
      this.updateForm(playerMoneyTransaction);

      this.playerService
        .query()
        .pipe(
          map((res: HttpResponse<IPlayer[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPlayer[]) => (this.players = resBody));
    });
  }

  updateForm(playerMoneyTransaction: IPlayerMoneyTransaction): void {
    this.editForm.patchValue({
      id: playerMoneyTransaction.id,
      time: playerMoneyTransaction.time != null ? playerMoneyTransaction.time.format(DATE_TIME_FORMAT) : null,
      transaction: playerMoneyTransaction.transaction,
      value: playerMoneyTransaction.value,
      playerId: playerMoneyTransaction.playerId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const playerMoneyTransaction = this.createFromForm();
    if (playerMoneyTransaction.id !== undefined) {
      this.subscribeToSaveResponse(this.playerMoneyTransactionService.update(playerMoneyTransaction));
    } else {
      this.subscribeToSaveResponse(this.playerMoneyTransactionService.create(playerMoneyTransaction));
    }
  }

  private createFromForm(): IPlayerMoneyTransaction {
    return {
      ...new PlayerMoneyTransaction(),
      id: this.editForm.get(['id'])!.value,
      time: this.editForm.get(['time'])!.value != null ? moment(this.editForm.get(['time'])!.value, DATE_TIME_FORMAT) : undefined,
      transaction: this.editForm.get(['transaction'])!.value,
      value: this.editForm.get(['value'])!.value,
      playerId: this.editForm.get(['playerId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlayerMoneyTransaction>>): void {
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

  trackById(index: number, item: IPlayer): any {
    return item.id;
  }
}
