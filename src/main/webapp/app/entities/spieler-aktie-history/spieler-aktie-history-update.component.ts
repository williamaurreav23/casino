import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ISpielerAktieHistory, SpielerAktieHistory } from 'app/shared/model/spieler-aktie-history.model';
import { SpielerAktieHistoryService } from './spieler-aktie-history.service';
import { ISpieler } from 'app/shared/model/spieler.model';
import { SpielerService } from 'app/entities/spieler/spieler.service';
import { IAktie } from 'app/shared/model/aktie.model';
import { AktieService } from 'app/entities/aktie/aktie.service';

@Component({
  selector: 'jhi-spieler-aktie-history-update',
  templateUrl: './spieler-aktie-history-update.component.html'
})
export class SpielerAktieHistoryUpdateComponent implements OnInit {
  isSaving: boolean;

  spielers: ISpieler[];

  akties: IAktie[];

  editForm = this.fb.group({
    id: [],
    anzahl: [],
    creationTime: [],
    spieler: [],
    aktie: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected spielerAktieHistoryService: SpielerAktieHistoryService,
    protected spielerService: SpielerService,
    protected aktieService: AktieService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ spielerAktieHistory }) => {
      this.updateForm(spielerAktieHistory);
    });
    this.spielerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISpieler[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISpieler[]>) => response.body)
      )
      .subscribe(
        (res: ISpieler[]) => (this.spielers = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.aktieService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAktie[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAktie[]>) => response.body)
      )
      .subscribe(
        (res: IAktie[]) => (this.akties = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(spielerAktieHistory: ISpielerAktieHistory) {
    this.editForm.patchValue({
      id: spielerAktieHistory.id,
      anzahl: spielerAktieHistory.anzahl,
      creationTime: spielerAktieHistory.creationTime != null ? spielerAktieHistory.creationTime.format(DATE_TIME_FORMAT) : null,
      spieler: spielerAktieHistory.spieler,
      aktie: spielerAktieHistory.aktie
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const spielerAktieHistory = this.createFromForm();
    if (spielerAktieHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.spielerAktieHistoryService.update(spielerAktieHistory));
    } else {
      this.subscribeToSaveResponse(this.spielerAktieHistoryService.create(spielerAktieHistory));
    }
  }

  private createFromForm(): ISpielerAktieHistory {
    return {
      ...new SpielerAktieHistory(),
      id: this.editForm.get(['id']).value,
      anzahl: this.editForm.get(['anzahl']).value,
      creationTime:
        this.editForm.get(['creationTime']).value != null ? moment(this.editForm.get(['creationTime']).value, DATE_TIME_FORMAT) : undefined,
      spieler: this.editForm.get(['spieler']).value,
      aktie: this.editForm.get(['aktie']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpielerAktieHistory>>) {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackSpielerById(index: number, item: ISpieler) {
    return item.id;
  }

  trackAktieById(index: number, item: IAktie) {
    return item.id;
  }
}
