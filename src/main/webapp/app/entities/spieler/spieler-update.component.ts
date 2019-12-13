import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISpieler, Spieler } from 'app/shared/model/spieler.model';
import { SpielerService } from './spieler.service';

@Component({
  selector: 'jhi-spieler-update',
  templateUrl: './spieler-update.component.html'
})
export class SpielerUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    vorname: [],
    nachname: [],
    isKind: [],
    kennzahl: []
  });

  constructor(protected spielerService: SpielerService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ spieler }) => {
      this.updateForm(spieler);
    });
  }

  updateForm(spieler: ISpieler) {
    this.editForm.patchValue({
      id: spieler.id,
      vorname: spieler.vorname,
      nachname: spieler.nachname,
      isKind: spieler.isKind,
      kennzahl: spieler.kennzahl
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const spieler = this.createFromForm();
    if (spieler.id !== undefined) {
      this.subscribeToSaveResponse(this.spielerService.update(spieler));
    } else {
      this.subscribeToSaveResponse(this.spielerService.create(spieler));
    }
  }

  private createFromForm(): ISpieler {
    return {
      ...new Spieler(),
      id: this.editForm.get(['id']).value,
      vorname: this.editForm.get(['vorname']).value,
      nachname: this.editForm.get(['nachname']).value,
      isKind: this.editForm.get(['isKind']).value,
      kennzahl: this.editForm.get(['kennzahl']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpieler>>) {
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
}
