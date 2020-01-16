import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStockValueChange } from 'app/shared/model/stock-value-change.model';
import { StockValueChangeService } from './stock-value-change.service';

@Component({
  templateUrl: './stock-value-change-delete-dialog.component.html'
})
export class StockValueChangeDeleteDialogComponent {
  stockValueChange?: IStockValueChange;

  constructor(
    protected stockValueChangeService: StockValueChangeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.stockValueChangeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('stockValueChangeListModification');
      this.activeModal.close();
    });
  }
}
