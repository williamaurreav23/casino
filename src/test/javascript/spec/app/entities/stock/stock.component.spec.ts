import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CasinoTestModule } from '../../../test.module';
import { StockComponent } from 'app/entities/stock/stock.component';
import { StockService } from 'app/entities/stock/stock.service';
import { Stock } from 'app/shared/model/stock.model';

describe('Component Tests', () => {
  describe('Stock Management Component', () => {
    let comp: StockComponent;
    let fixture: ComponentFixture<StockComponent>;
    let service: StockService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [StockComponent],
        providers: []
      })
        .overrideTemplate(StockComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StockComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StockService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Stock(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.stocks && comp.stocks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
