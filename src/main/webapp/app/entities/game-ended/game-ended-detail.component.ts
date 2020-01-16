import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGameEnded } from 'app/shared/model/game-ended.model';

@Component({
  selector: 'jhi-game-ended-detail',
  templateUrl: './game-ended-detail.component.html'
})
export class GameEndedDetailComponent implements OnInit {
  gameEnded: IGameEnded | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gameEnded }) => {
      this.gameEnded = gameEnded;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
