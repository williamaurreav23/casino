import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CasinoTestModule } from '../../../test.module';
import { PlayerMoneyTransactionDetailComponent } from 'app/entities/player-money-transaction/player-money-transaction-detail.component';
import { PlayerMoneyTransaction } from 'app/shared/model/player-money-transaction.model';

describe('Component Tests', () => {
  describe('PlayerMoneyTransaction Management Detail Component', () => {
    let comp: PlayerMoneyTransactionDetailComponent;
    let fixture: ComponentFixture<PlayerMoneyTransactionDetailComponent>;
    const route = ({ data: of({ playerMoneyTransaction: new PlayerMoneyTransaction(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [PlayerMoneyTransactionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PlayerMoneyTransactionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlayerMoneyTransactionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load playerMoneyTransaction on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.playerMoneyTransaction).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
