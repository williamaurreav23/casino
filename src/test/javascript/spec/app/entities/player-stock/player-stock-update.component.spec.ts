import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CasinoTestModule } from '../../../test.module';
import { PlayerStockUpdateComponent } from 'app/entities/player-stock/player-stock-update.component';
import { PlayerStockService } from 'app/entities/player-stock/player-stock.service';
import { PlayerStock } from 'app/shared/model/player-stock.model';

describe('Component Tests', () => {
  describe('PlayerStock Management Update Component', () => {
    let comp: PlayerStockUpdateComponent;
    let fixture: ComponentFixture<PlayerStockUpdateComponent>;
    let service: PlayerStockService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [PlayerStockUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PlayerStockUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlayerStockUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlayerStockService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PlayerStock(123);
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
        const entity = new PlayerStock();
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
