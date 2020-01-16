import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CasinoTestModule } from '../../../test.module';
import { PlayerStockDetailComponent } from 'app/entities/player-stock/player-stock-detail.component';
import { PlayerStock } from 'app/shared/model/player-stock.model';

describe('Component Tests', () => {
  describe('PlayerStock Management Detail Component', () => {
    let comp: PlayerStockDetailComponent;
    let fixture: ComponentFixture<PlayerStockDetailComponent>;
    const route = ({ data: of({ playerStock: new PlayerStock(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [PlayerStockDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PlayerStockDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlayerStockDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load playerStock on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.playerStock).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
