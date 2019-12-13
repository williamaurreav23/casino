import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAktieWert } from 'app/shared/model/aktie-wert.model';
import { AktieWertService } from './aktie-wert.service';

@Component({
  selector: 'jhi-aktie-wert-delete-dialog',
  templateUrl: './aktie-wert-delete-dialog.component.html'
})
export class AktieWertDeleteDialogComponent {
  aktieWert: IAktieWert;

  constructor(protected aktieWertService: AktieWertService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.aktieWertService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'aktieWertListModification',
        content: 'Deleted an aktieWert'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-aktie-wert-delete-popup',
  template: ''
})
export class AktieWertDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ aktieWert }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AktieWertDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.aktieWert = aktieWert;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/aktie-wert', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/aktie-wert', { outlets: { popup: null } }]);
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
