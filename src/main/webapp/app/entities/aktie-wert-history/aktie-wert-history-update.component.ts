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
import { IAktieWertHistory, AktieWertHistory } from 'app/shared/model/aktie-wert-history.model';
import { AktieWertHistoryService } from './aktie-wert-history.service';
import { IAktie } from 'app/shared/model/aktie.model';
import { AktieService } from 'app/entities/aktie/aktie.service';

@Component({
  selector: 'jhi-aktie-wert-history-update',
  templateUrl: './aktie-wert-history-update.component.html'
})
export class AktieWertHistoryUpdateComponent implements OnInit {
  isSaving: boolean;

  akties: IAktie[];

  editForm = this.fb.group({
    id: [],
    wert: [],
    creationTime: [],
    aktie: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected aktieWertHistoryService: AktieWertHistoryService,
    protected aktieService: AktieService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ aktieWertHistory }) => {
      this.updateForm(aktieWertHistory);
    });
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

  updateForm(aktieWertHistory: IAktieWertHistory) {
    this.editForm.patchValue({
      id: aktieWertHistory.id,
      wert: aktieWertHistory.wert,
      creationTime: aktieWertHistory.creationTime != null ? aktieWertHistory.creationTime.format(DATE_TIME_FORMAT) : null,
      aktie: aktieWertHistory.aktie
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const aktieWertHistory = this.createFromForm();
    if (aktieWertHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.aktieWertHistoryService.update(aktieWertHistory));
    } else {
      this.subscribeToSaveResponse(this.aktieWertHistoryService.create(aktieWertHistory));
    }
  }

  private createFromForm(): IAktieWertHistory {
    return {
      ...new AktieWertHistory(),
      id: this.editForm.get(['id']).value,
      wert: this.editForm.get(['wert']).value,
      creationTime:
        this.editForm.get(['creationTime']).value != null ? moment(this.editForm.get(['creationTime']).value, DATE_TIME_FORMAT) : undefined,
      aktie: this.editForm.get(['aktie']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAktieWertHistory>>) {
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

  trackAktieById(index: number, item: IAktie) {
    return item.id;
  }
}
