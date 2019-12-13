import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IAktie, Aktie } from 'app/shared/model/aktie.model';
import { AktieService } from './aktie.service';

@Component({
  selector: 'jhi-aktie-update',
  templateUrl: './aktie-update.component.html'
})
export class AktieUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: []
  });

  constructor(protected aktieService: AktieService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ aktie }) => {
      this.updateForm(aktie);
    });
  }

  updateForm(aktie: IAktie) {
    this.editForm.patchValue({
      id: aktie.id,
      name: aktie.name
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const aktie = this.createFromForm();
    if (aktie.id !== undefined) {
      this.subscribeToSaveResponse(this.aktieService.update(aktie));
    } else {
      this.subscribeToSaveResponse(this.aktieService.create(aktie));
    }
  }

  private createFromForm(): IAktie {
    return {
      ...new Aktie(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAktie>>) {
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
