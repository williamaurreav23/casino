import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { CasinoTestModule } from '../../../test.module';
import { PlayerMoneyTransactionUpdateComponent } from 'app/entities/player-money-transaction/player-money-transaction-update.component';
import { PlayerMoneyTransactionService } from 'app/entities/player-money-transaction/player-money-transaction.service';
import { PlayerMoneyTransaction } from 'app/shared/model/player-money-transaction.model';

describe('Component Tests', () => {
  describe('PlayerMoneyTransaction Management Update Component', () => {
    let comp: PlayerMoneyTransactionUpdateComponent;
    let fixture: ComponentFixture<PlayerMoneyTransactionUpdateComponent>;
    let service: PlayerMoneyTransactionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [PlayerMoneyTransactionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PlayerMoneyTransactionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlayerMoneyTransactionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlayerMoneyTransactionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PlayerMoneyTransaction(123);
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
        const entity = new PlayerMoneyTransaction();
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
