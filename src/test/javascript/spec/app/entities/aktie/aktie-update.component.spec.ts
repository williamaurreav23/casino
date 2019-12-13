import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CasinoTestModule } from '../../../test.module';
import { AktieUpdateComponent } from 'app/entities/aktie/aktie-update.component';
import { AktieService } from 'app/entities/aktie/aktie.service';
import { Aktie } from 'app/shared/model/aktie.model';

describe('Component Tests', () => {
  describe('Aktie Management Update Component', () => {
    let comp: AktieUpdateComponent;
    let fixture: ComponentFixture<AktieUpdateComponent>;
    let service: AktieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [AktieUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AktieUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AktieUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AktieService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Aktie(123);
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
        const entity = new Aktie();
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
