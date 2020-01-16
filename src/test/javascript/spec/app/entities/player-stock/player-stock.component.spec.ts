import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CasinoTestModule } from '../../../test.module';
import { PlayerStockComponent } from 'app/entities/player-stock/player-stock.component';
import { PlayerStockService } from 'app/entities/player-stock/player-stock.service';
import { PlayerStock } from 'app/shared/model/player-stock.model';

describe('Component Tests', () => {
  describe('PlayerStock Management Component', () => {
    let comp: PlayerStockComponent;
    let fixture: ComponentFixture<PlayerStockComponent>;
    let service: PlayerStockService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [PlayerStockComponent],
        providers: []
      })
        .overrideTemplate(PlayerStockComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlayerStockComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlayerStockService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PlayerStock(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.playerStocks && comp.playerStocks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
