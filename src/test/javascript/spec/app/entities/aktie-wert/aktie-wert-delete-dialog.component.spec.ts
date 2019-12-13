import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CasinoTestModule } from '../../../test.module';
import { AktieWertDeleteDialogComponent } from 'app/entities/aktie-wert/aktie-wert-delete-dialog.component';
import { AktieWertService } from 'app/entities/aktie-wert/aktie-wert.service';

describe('Component Tests', () => {
  describe('AktieWert Management Delete Component', () => {
    let comp: AktieWertDeleteDialogComponent;
    let fixture: ComponentFixture<AktieWertDeleteDialogComponent>;
    let service: AktieWertService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [AktieWertDeleteDialogComponent]
      })
        .overrideTemplate(AktieWertDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AktieWertDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AktieWertService);
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
