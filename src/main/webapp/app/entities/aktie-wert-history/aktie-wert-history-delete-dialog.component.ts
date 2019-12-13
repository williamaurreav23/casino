import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAktieWertHistory } from 'app/shared/model/aktie-wert-history.model';
import { AktieWertHistoryService } from './aktie-wert-history.service';

@Component({
  selector: 'jhi-aktie-wert-history-delete-dialog',
  templateUrl: './aktie-wert-history-delete-dialog.component.html'
})
export class AktieWertHistoryDeleteDialogComponent {
  aktieWertHistory: IAktieWertHistory;

  constructor(
    protected aktieWertHistoryService: AktieWertHistoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.aktieWertHistoryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'aktieWertHistoryListModification',
        content: 'Deleted an aktieWertHistory'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-aktie-wert-history-delete-popup',
  template: ''
})
export class AktieWertHistoryDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ aktieWertHistory }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AktieWertHistoryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.aktieWertHistory = aktieWertHistory;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/aktie-wert-history', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/aktie-wert-history', { outlets: { popup: null } }]);
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
