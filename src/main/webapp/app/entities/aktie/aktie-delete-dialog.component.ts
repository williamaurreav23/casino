import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAktie } from 'app/shared/model/aktie.model';
import { AktieService } from './aktie.service';

@Component({
  selector: 'jhi-aktie-delete-dialog',
  templateUrl: './aktie-delete-dialog.component.html'
})
export class AktieDeleteDialogComponent {
  aktie: IAktie;

  constructor(protected aktieService: AktieService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.aktieService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'aktieListModification',
        content: 'Deleted an aktie'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-aktie-delete-popup',
  template: ''
})
export class AktieDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ aktie }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AktieDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.aktie = aktie;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/aktie', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/aktie', { outlets: { popup: null } }]);
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
