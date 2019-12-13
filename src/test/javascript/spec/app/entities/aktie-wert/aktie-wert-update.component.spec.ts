import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CasinoTestModule } from '../../../test.module';
import { AktieWertUpdateComponent } from 'app/entities/aktie-wert/aktie-wert-update.component';
import { AktieWertService } from 'app/entities/aktie-wert/aktie-wert.service';
import { AktieWert } from 'app/shared/model/aktie-wert.model';

describe('Component Tests', () => {
  describe('AktieWert Management Update Component', () => {
    let comp: AktieWertUpdateComponent;
    let fixture: ComponentFixture<AktieWertUpdateComponent>;
    let service: AktieWertService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [AktieWertUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AktieWertUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AktieWertUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AktieWertService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AktieWert(123);
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
        const entity = new AktieWert();
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
