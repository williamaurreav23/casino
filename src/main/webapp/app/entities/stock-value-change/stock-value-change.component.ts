import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IStockValueChange } from 'app/shared/model/stock-value-change.model';
import { StockValueChangeService } from './stock-value-change.service';
import { StockValueChangeDeleteDialogComponent } from './stock-value-change-delete-dialog.component';

@Component({
  selector: 'jhi-stock-value-change',
  templateUrl: './stock-value-change.component.html'
})
export class StockValueChangeComponent implements OnInit, OnDestroy {
  stockValueChanges?: IStockValueChange[];
  eventSubscriber?: Subscription;

  constructor(
    protected stockValueChangeService: StockValueChangeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.stockValueChangeService.query().subscribe((res: HttpResponse<IStockValueChange[]>) => {
      this.stockValueChanges = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInStockValueChanges();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IStockValueChange): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInStockValueChanges(): void {
    this.eventSubscriber = this.eventManager.subscribe('stockValueChangeListModification', () => this.loadAll());
  }

  delete(stockValueChange: IStockValueChange): void {
    const modalRef = this.modalService.open(StockValueChangeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.stockValueChange = stockValueChange;
  }
}
