import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISpieler } from 'app/shared/model/spieler.model';
import { SpielerService } from './spieler.service';

@Component({
  selector: 'jhi-spieler-delete-dialog',
  templateUrl: './spieler-delete-dialog.component.html'
})
export class SpielerDeleteDialogComponent {
  spieler: ISpieler;

  constructor(protected spielerService: SpielerService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.spielerService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'spielerListModification',
        content: 'Deleted an spieler'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-spieler-delete-popup',
  template: ''
})
export class SpielerDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ spieler }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SpielerDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.spieler = spieler;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/spieler', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/spieler', { outlets: { popup: null } }]);
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
