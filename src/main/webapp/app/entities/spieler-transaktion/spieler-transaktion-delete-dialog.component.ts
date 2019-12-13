import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISpielerTransaktion } from 'app/shared/model/spieler-transaktion.model';
import { SpielerTransaktionService } from './spieler-transaktion.service';

@Component({
  selector: 'jhi-spieler-transaktion-delete-dialog',
  templateUrl: './spieler-transaktion-delete-dialog.component.html'
})
export class SpielerTransaktionDeleteDialogComponent {
  spielerTransaktion: ISpielerTransaktion;

  constructor(
    protected spielerTransaktionService: SpielerTransaktionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.spielerTransaktionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'spielerTransaktionListModification',
        content: 'Deleted an spielerTransaktion'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-spieler-transaktion-delete-popup',
  template: ''
})
export class SpielerTransaktionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ spielerTransaktion }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SpielerTransaktionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.spielerTransaktion = spielerTransaktion;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/spieler-transaktion', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/spieler-transaktion', { outlets: { popup: null } }]);
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
