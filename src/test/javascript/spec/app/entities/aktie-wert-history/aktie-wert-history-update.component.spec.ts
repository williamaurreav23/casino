import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CasinoTestModule } from '../../../test.module';
import { AktieWertHistoryUpdateComponent } from 'app/entities/aktie-wert-history/aktie-wert-history-update.component';
import { AktieWertHistoryService } from 'app/entities/aktie-wert-history/aktie-wert-history.service';
import { AktieWertHistory } from 'app/shared/model/aktie-wert-history.model';

describe('Component Tests', () => {
  describe('AktieWertHistory Management Update Component', () => {
    let comp: AktieWertHistoryUpdateComponent;
    let fixture: ComponentFixture<AktieWertHistoryUpdateComponent>;
    let service: AktieWertHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [AktieWertHistoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AktieWertHistoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AktieWertHistoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AktieWertHistoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AktieWertHistory(123);
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
        const entity = new AktieWertHistory();
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
