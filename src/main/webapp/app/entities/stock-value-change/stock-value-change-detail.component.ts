import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStockValueChange } from 'app/shared/model/stock-value-change.model';

@Component({
  selector: 'jhi-stock-value-change-detail',
  templateUrl: './stock-value-change-detail.component.html'
})
export class StockValueChangeDetailComponent implements OnInit {
  stockValueChange: IStockValueChange | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stockValueChange }) => {
      this.stockValueChange = stockValueChange;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
