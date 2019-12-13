import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAktieWert, AktieWert } from 'app/shared/model/aktie-wert.model';
import { AktieWertService } from './aktie-wert.service';
import { IAktie } from 'app/shared/model/aktie.model';
import { AktieService } from 'app/entities/aktie/aktie.service';

@Component({
  selector: 'jhi-aktie-wert-update',
  templateUrl: './aktie-wert-update.component.html'
})
export class AktieWertUpdateComponent implements OnInit {
  isSaving: boolean;

  akties: IAktie[];

  editForm = this.fb.group({
    id: [],
    wert: [],
    aktieId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected aktieWertService: AktieWertService,
    protected aktieService: AktieService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ aktieWert }) => {
      this.updateForm(aktieWert);
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

  updateForm(aktieWert: IAktieWert) {
    this.editForm.patchValue({
      id: aktieWert.id,
      wert: aktieWert.wert,
      aktieId: aktieWert.aktieId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const aktieWert = this.createFromForm();
    if (aktieWert.id !== undefined) {
      this.subscribeToSaveResponse(this.aktieWertService.update(aktieWert));
    } else {
      this.subscribeToSaveResponse(this.aktieWertService.create(aktieWert));
    }
  }

  private createFromForm(): IAktieWert {
    return {
      ...new AktieWert(),
      id: this.editForm.get(['id']).value,
      wert: this.editForm.get(['wert']).value,
      aktieId: this.editForm.get(['aktieId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAktieWert>>) {
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
