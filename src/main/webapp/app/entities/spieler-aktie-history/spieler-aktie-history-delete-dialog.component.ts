import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISpielerAktieHistory } from 'app/shared/model/spieler-aktie-history.model';
import { SpielerAktieHistoryService } from './spieler-aktie-history.service';

@Component({
  selector: 'jhi-spieler-aktie-history-delete-dialog',
  templateUrl: './spieler-aktie-history-delete-dialog.component.html'
})
export class SpielerAktieHistoryDeleteDialogComponent {
  spielerAktieHistory: ISpielerAktieHistory;

  constructor(
    protected spielerAktieHistoryService: SpielerAktieHistoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.spielerAktieHistoryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'spielerAktieHistoryListModification',
        content: 'Deleted an spielerAktieHistory'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-spieler-aktie-history-delete-popup',
  template: ''
})
export class SpielerAktieHistoryDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ spielerAktieHistory }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SpielerAktieHistoryDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.spielerAktieHistory = spielerAktieHistory;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/spieler-aktie-history', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/spieler-aktie-history', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
