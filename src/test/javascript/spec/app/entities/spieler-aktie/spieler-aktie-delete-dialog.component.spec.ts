import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CasinoTestModule } from '../../../test.module';
import { SpielerAktieDeleteDialogComponent } from 'app/entities/spieler-aktie/spieler-aktie-delete-dialog.component';
import { SpielerAktieService } from 'app/entities/spieler-aktie/spieler-aktie.service';

describe('Component Tests', () => {
  describe('SpielerAktie Management Delete Component', () => {
    let comp: SpielerAktieDeleteDialogComponent;
    let fixture: ComponentFixture<SpielerAktieDeleteDialogComponent>;
    let service: SpielerAktieService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [SpielerAktieDeleteDialogComponent]
      })
        .overrideTemplate(SpielerAktieDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SpielerAktieDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpielerAktieService);
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
