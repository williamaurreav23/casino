import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CasinoTestModule } from '../../../test.module';
import { SpielerTransaktionUpdateComponent } from 'app/entities/spieler-transaktion/spieler-transaktion-update.component';
import { SpielerTransaktionService } from 'app/entities/spieler-transaktion/spieler-transaktion.service';
import { SpielerTransaktion } from 'app/shared/model/spieler-transaktion.model';

describe('Component Tests', () => {
  describe('SpielerTransaktion Management Update Component', () => {
    let comp: SpielerTransaktionUpdateComponent;
    let fixture: ComponentFixture<SpielerTransaktionUpdateComponent>;
    let service: SpielerTransaktionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [SpielerTransaktionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SpielerTransaktionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SpielerTransaktionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpielerTransaktionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SpielerTransaktion(123);
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
        const entity = new SpielerTransaktion();
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
