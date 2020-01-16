import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CasinoTestModule } from '../../../test.module';
import { GameEndedUpdateComponent } from 'app/entities/game-ended/game-ended-update.component';
import { GameEndedService } from 'app/entities/game-ended/game-ended.service';
import { GameEnded } from 'app/shared/model/game-ended.model';

describe('Component Tests', () => {
  describe('GameEnded Management Update Component', () => {
    let comp: GameEndedUpdateComponent;
    let fixture: ComponentFixture<GameEndedUpdateComponent>;
    let service: GameEndedService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [GameEndedUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GameEndedUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GameEndedUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GameEndedService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GameEnded(123);
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
        const entity = new GameEnded();
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
