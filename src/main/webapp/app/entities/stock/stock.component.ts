import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IStock } from 'app/shared/model/stock.model';
import { StockService } from './stock.service';
import { StockDeleteDialogComponent } from './stock-delete-dialog.component';

@Component({
  selector: 'jhi-stock',
  templateUrl: './stock.component.html'
})
export class StockComponent implements OnInit, OnDestroy {
  stocks?: IStock[];
  eventSubscriber?: Subscription;

  constructor(protected stockService: StockService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.stockService.query().subscribe((res: HttpResponse<IStock[]>) => {
      this.stocks = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInStocks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IStock): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInStocks(): void {
    this.eventSubscriber = this.eventManager.subscribe('stockListModification', () => this.loadAll());
  }

  delete(stock: IStock): void {
    const modalRef = this.modalService.open(StockDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.stock = stock;
  }
}
