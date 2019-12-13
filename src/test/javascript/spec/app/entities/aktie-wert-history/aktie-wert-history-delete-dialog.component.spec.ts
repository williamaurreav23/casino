import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CasinoTestModule } from '../../../test.module';
import { AktieWertHistoryDeleteDialogComponent } from 'app/entities/aktie-wert-history/aktie-wert-history-delete-dialog.component';
import { AktieWertHistoryService } from 'app/entities/aktie-wert-history/aktie-wert-history.service';

describe('Component Tests', () => {
  describe('AktieWertHistory Management Delete Component', () => {
    let comp: AktieWertHistoryDeleteDialogComponent;
    let fixture: ComponentFixture<AktieWertHistoryDeleteDialogComponent>;
    let service: AktieWertHistoryService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [AktieWertHistoryDeleteDialogComponent]
      })
        .overrideTemplate(AktieWertHistoryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AktieWertHistoryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AktieWertHistoryService);
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
