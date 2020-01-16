import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CasinoTestModule } from '../../../test.module';
import { PlayerStockTransactionDetailComponent } from 'app/entities/player-stock-transaction/player-stock-transaction-detail.component';
import { PlayerStockTransaction } from 'app/shared/model/player-stock-transaction.model';

describe('Component Tests', () => {
  describe('PlayerStockTransaction Management Detail Component', () => {
    let comp: PlayerStockTransactionDetailComponent;
    let fixture: ComponentFixture<PlayerStockTransactionDetailComponent>;
    const route = ({ data: of({ playerStockTransaction: new PlayerStockTransaction(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [PlayerStockTransactionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PlayerStockTransactionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlayerStockTransactionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load playerStockTransaction on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.playerStockTransaction).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
