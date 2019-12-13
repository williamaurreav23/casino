import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISpielerAktie } from 'app/shared/model/spieler-aktie.model';
import { SpielerAktieService } from './spieler-aktie.service';

@Component({
  selector: 'jhi-spieler-aktie-delete-dialog',
  templateUrl: './spieler-aktie-delete-dialog.component.html'
})
export class SpielerAktieDeleteDialogComponent {
  spielerAktie: ISpielerAktie;

  constructor(
    protected spielerAktieService: SpielerAktieService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.spielerAktieService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'spielerAktieListModification',
        content: 'Deleted an spielerAktie'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-spieler-aktie-delete-popup',
  template: ''
})
export class SpielerAktieDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ spielerAktie }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SpielerAktieDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.spielerAktie = spielerAktie;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/spieler-aktie', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/spieler-aktie', { outlets: { popup: null } }]);
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
