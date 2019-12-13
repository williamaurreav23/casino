import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CasinoTestModule } from '../../../test.module';
import { SpielerUpdateComponent } from 'app/entities/spieler/spieler-update.component';
import { SpielerService } from 'app/entities/spieler/spieler.service';
import { Spieler } from 'app/shared/model/spieler.model';

describe('Component Tests', () => {
  describe('Spieler Management Update Component', () => {
    let comp: SpielerUpdateComponent;
    let fixture: ComponentFixture<SpielerUpdateComponent>;
    let service: SpielerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [SpielerUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SpielerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SpielerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpielerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Spieler(123);
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
        const entity = new Spieler();
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
