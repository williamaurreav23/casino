import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CasinoTestModule } from '../../../test.module';
import { StockValueChangeUpdateComponent } from 'app/entities/stock-value-change/stock-value-change-update.component';
import { StockValueChangeService } from 'app/entities/stock-value-change/stock-value-change.service';
import { StockValueChange } from 'app/shared/model/stock-value-change.model';

describe('Component Tests', () => {
  describe('StockValueChange Management Update Component', () => {
    let comp: StockValueChangeUpdateComponent;
    let fixture: ComponentFixture<StockValueChangeUpdateComponent>;
    let service: StockValueChangeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [StockValueChangeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(StockValueChangeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StockValueChangeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StockValueChangeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new StockValueChange(123);
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
        const entity = new StockValueChange();
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
