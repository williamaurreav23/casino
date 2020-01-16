import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IGameEnded, GameEnded } from 'app/shared/model/game-ended.model';
import { GameEndedService } from './game-ended.service';

@Component({
  selector: 'jhi-game-ended-update',
  templateUrl: './game-ended-update.component.html'
})
export class GameEndedUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: []
  });

  constructor(protected gameEndedService: GameEndedService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gameEnded }) => {
      this.updateForm(gameEnded);
    });
  }

  updateForm(gameEnded: IGameEnded): void {
    this.editForm.patchValue({
      id: gameEnded.id
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const gameEnded = this.createFromForm();
    if (gameEnded.id !== undefined) {
      this.subscribeToSaveResponse(this.gameEndedService.update(gameEnded));
    } else {
      this.subscribeToSaveResponse(this.gameEndedService.create(gameEnded));
    }
  }

  private createFromForm(): IGameEnded {
    return {
      ...new GameEnded(),
      id: this.editForm.get(['id'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGameEnded>>): void {
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
}
