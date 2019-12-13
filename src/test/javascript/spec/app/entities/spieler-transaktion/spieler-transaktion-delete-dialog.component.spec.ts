import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CasinoTestModule } from '../../../test.module';
import { SpielerTransaktionDeleteDialogComponent } from 'app/entities/spieler-transaktion/spieler-transaktion-delete-dialog.component';
import { SpielerTransaktionService } from 'app/entities/spieler-transaktion/spieler-transaktion.service';

describe('Component Tests', () => {
  describe('SpielerTransaktion Management Delete Component', () => {
    let comp: SpielerTransaktionDeleteDialogComponent;
    let fixture: ComponentFixture<SpielerTransaktionDeleteDialogComponent>;
    let service: SpielerTransaktionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [SpielerTransaktionDeleteDialogComponent]
      })
        .overrideTemplate(SpielerTransaktionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SpielerTransaktionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpielerTransaktionService);
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
