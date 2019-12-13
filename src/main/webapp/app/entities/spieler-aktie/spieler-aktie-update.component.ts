import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISpielerAktie, SpielerAktie } from 'app/shared/model/spieler-aktie.model';
import { SpielerAktieService } from './spieler-aktie.service';
import { ISpieler } from 'app/shared/model/spieler.model';
import { SpielerService } from 'app/entities/spieler/spieler.service';
import { IAktie } from 'app/shared/model/aktie.model';
import { AktieService } from 'app/entities/aktie/aktie.service';

@Component({
  selector: 'jhi-spieler-aktie-update',
  templateUrl: './spieler-aktie-update.component.html'
})
export class SpielerAktieUpdateComponent implements OnInit {
  isSaving: boolean;

  spielers: ISpieler[];

  akties: IAktie[];

  editForm = this.fb.group({
    id: [],
    anzahl: [],
    spielerId: [],
    aktieId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected spielerAktieService: SpielerAktieService,
    protected spielerService: SpielerService,
    protected aktieService: AktieService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ spielerAktie }) => {
      this.updateForm(spielerAktie);
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

  updateForm(spielerAktie: ISpielerAktie) {
    this.editForm.patchValue({
      id: spielerAktie.id,
      anzahl: spielerAktie.anzahl,
      spielerId: spielerAktie.spielerId,
      aktieId: spielerAktie.aktieId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const spielerAktie = this.createFromForm();
    if (spielerAktie.id !== undefined) {
      this.subscribeToSaveResponse(this.spielerAktieService.update(spielerAktie));
    } else {
      this.subscribeToSaveResponse(this.spielerAktieService.create(spielerAktie));
    }
  }

  private createFromForm(): ISpielerAktie {
    return {
      ...new SpielerAktie(),
      id: this.editForm.get(['id']).value,
      anzahl: this.editForm.get(['anzahl']).value,
      spielerId: this.editForm.get(['spielerId']).value,
      aktieId: this.editForm.get(['aktieId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpielerAktie>>) {
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
