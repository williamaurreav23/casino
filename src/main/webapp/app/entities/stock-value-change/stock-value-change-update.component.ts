import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IStockValueChange, StockValueChange } from 'app/shared/model/stock-value-change.model';
import { StockValueChangeService } from './stock-value-change.service';
import { IStock } from 'app/shared/model/stock.model';
import { StockService } from 'app/entities/stock/stock.service';

@Component({
  selector: 'jhi-stock-value-change-update',
  templateUrl: './stock-value-change-update.component.html'
})
export class StockValueChangeUpdateComponent implements OnInit {
  isSaving = false;

  stocks: IStock[] = [];

  editForm = this.fb.group({
    id: [],
    time: [],
    value: [],
    stockId: []
  });

  constructor(
    protected stockValueChangeService: StockValueChangeService,
    protected stockService: StockService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stockValueChange }) => {
      this.updateForm(stockValueChange);

      this.stockService
        .query()
        .pipe(
          map((res: HttpResponse<IStock[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IStock[]) => (this.stocks = resBody));
    });
  }

  updateForm(stockValueChange: IStockValueChange): void {
    this.editForm.patchValue({
      id: stockValueChange.id,
      time: stockValueChange.time != null ? stockValueChange.time.format(DATE_TIME_FORMAT) : null,
      value: stockValueChange.value,
      stockId: stockValueChange.stockId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const stockValueChange = this.createFromForm();
    if (stockValueChange.id !== undefined) {
      this.subscribeToSaveResponse(this.stockValueChangeService.update(stockValueChange));
    } else {
      this.subscribeToSaveResponse(this.stockValueChangeService.create(stockValueChange));
    }
  }

  private createFromForm(): IStockValueChange {
    return {
      ...new StockValueChange(),
      id: this.editForm.get(['id'])!.value,
      time: this.editForm.get(['time'])!.value != null ? moment(this.editForm.get(['time'])!.value, DATE_TIME_FORMAT) : undefined,
      value: this.editForm.get(['value'])!.value,
      stockId: this.editForm.get(['stockId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockValueChange>>): void {
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

  trackById(index: number, item: IStock): any {
    return item.id;
  }
}
