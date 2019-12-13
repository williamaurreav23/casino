import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISpielerTransaktion, SpielerTransaktion } from 'app/shared/model/spieler-transaktion.model';
import { SpielerTransaktionService } from './spieler-transaktion.service';
import { ISpieler } from 'app/shared/model/spieler.model';
import { SpielerService } from 'app/entities/spieler/spieler.service';

@Component({
  selector: 'jhi-spieler-transaktion-update',
  templateUrl: './spieler-transaktion-update.component.html'
})
export class SpielerTransaktionUpdateComponent implements OnInit {
  isSaving: boolean;

  spielers: ISpieler[];

  editForm = this.fb.group({
    id: [],
    wert: [],
    typ: [],
    spielerId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected spielerTransaktionService: SpielerTransaktionService,
    protected spielerService: SpielerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ spielerTransaktion }) => {
      this.updateForm(spielerTransaktion);
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
  }

  updateForm(spielerTransaktion: ISpielerTransaktion) {
    this.editForm.patchValue({
      id: spielerTransaktion.id,
      wert: spielerTransaktion.wert,
      typ: spielerTransaktion.typ,
      spielerId: spielerTransaktion.spielerId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const spielerTransaktion = this.createFromForm();
    if (spielerTransaktion.id !== undefined) {
      this.subscribeToSaveResponse(this.spielerTransaktionService.update(spielerTransaktion));
    } else {
      this.subscribeToSaveResponse(this.spielerTransaktionService.create(spielerTransaktion));
    }
  }

  private createFromForm(): ISpielerTransaktion {
    return {
      ...new SpielerTransaktion(),
      id: this.editForm.get(['id']).value,
      wert: this.editForm.get(['wert']).value,
      typ: this.editForm.get(['typ']).value,
      spielerId: this.editForm.get(['spielerId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpielerTransaktion>>) {
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
}
