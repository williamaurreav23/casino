import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CasinoTestModule } from '../../../test.module';
import { SpielerAktieHistoryUpdateComponent } from 'app/entities/spieler-aktie-history/spieler-aktie-history-update.component';
import { SpielerAktieHistoryService } from 'app/entities/spieler-aktie-history/spieler-aktie-history.service';
import { SpielerAktieHistory } from 'app/shared/model/spieler-aktie-history.model';

describe('Component Tests', () => {
  describe('SpielerAktieHistory Management Update Component', () => {
    let comp: SpielerAktieHistoryUpdateComponent;
    let fixture: ComponentFixture<SpielerAktieHistoryUpdateComponent>;
    let service: SpielerAktieHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [SpielerAktieHistoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SpielerAktieHistoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SpielerAktieHistoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpielerAktieHistoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SpielerAktieHistory(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new SpielerAktieHistory();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
