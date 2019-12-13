import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CasinoTestModule } from '../../../test.module';
import { SpielerAktieUpdateComponent } from 'app/entities/spieler-aktie/spieler-aktie-update.component';
import { SpielerAktieService } from 'app/entities/spieler-aktie/spieler-aktie.service';
import { SpielerAktie } from 'app/shared/model/spieler-aktie.model';

describe('Component Tests', () => {
  describe('SpielerAktie Management Update Component', () => {
    let comp: SpielerAktieUpdateComponent;
    let fixture: ComponentFixture<SpielerAktieUpdateComponent>;
    let service: SpielerAktieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [SpielerAktieUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SpielerAktieUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SpielerAktieUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpielerAktieService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SpielerAktie(123);
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
        const entity = new SpielerAktie();
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
