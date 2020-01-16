import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CasinoTestModule } from '../../../test.module';
import { PlayerMoneyTransactionComponent } from 'app/entities/player-money-transaction/player-money-transaction.component';
import { PlayerMoneyTransactionService } from 'app/entities/player-money-transaction/player-money-transaction.service';
import { PlayerMoneyTransaction } from 'app/shared/model/player-money-transaction.model';

describe('Component Tests', () => {
  describe('PlayerMoneyTransaction Management Component', () => {
    let comp: PlayerMoneyTransactionComponent;
    let fixture: ComponentFixture<PlayerMoneyTransactionComponent>;
    let service: PlayerMoneyTransactionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [PlayerMoneyTransactionComponent],
        providers: []
      })
        .overrideTemplate(PlayerMoneyTransactionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlayerMoneyTransactionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlayerMoneyTransactionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PlayerMoneyTransaction(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.playerMoneyTransactions && comp.playerMoneyTransactions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
