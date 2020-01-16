import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CasinoTestModule } from '../../../test.module';
import { PlayerStockTransactionComponent } from 'app/entities/player-stock-transaction/player-stock-transaction.component';
import { PlayerStockTransactionService } from 'app/entities/player-stock-transaction/player-stock-transaction.service';
import { PlayerStockTransaction } from 'app/shared/model/player-stock-transaction.model';

describe('Component Tests', () => {
  describe('PlayerStockTransaction Management Component', () => {
    let comp: PlayerStockTransactionComponent;
    let fixture: ComponentFixture<PlayerStockTransactionComponent>;
    let service: PlayerStockTransactionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [PlayerStockTransactionComponent],
        providers: []
      })
        .overrideTemplate(PlayerStockTransactionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlayerStockTransactionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlayerStockTransactionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PlayerStockTransaction(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.playerStockTransactions && comp.playerStockTransactions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
