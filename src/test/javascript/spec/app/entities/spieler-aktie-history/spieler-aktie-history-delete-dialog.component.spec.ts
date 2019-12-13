import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CasinoTestModule } from '../../../test.module';
import { SpielerAktieHistoryDeleteDialogComponent } from 'app/entities/spieler-aktie-history/spieler-aktie-history-delete-dialog.component';
import { SpielerAktieHistoryService } from 'app/entities/spieler-aktie-history/spieler-aktie-history.service';

describe('Component Tests', () => {
  describe('SpielerAktieHistory Management Delete Component', () => {
    let comp: SpielerAktieHistoryDeleteDialogComponent;
    let fixture: ComponentFixture<SpielerAktieHistoryDeleteDialogComponent>;
    let service: SpielerAktieHistoryService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [SpielerAktieHistoryDeleteDialogComponent]
      })
        .overrideTemplate(SpielerAktieHistoryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SpielerAktieHistoryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpielerAktieHistoryService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
