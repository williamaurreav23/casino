import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CasinoTestModule } from '../../../test.module';
import { StockValueChangeComponent } from 'app/entities/stock-value-change/stock-value-change.component';
import { StockValueChangeService } from 'app/entities/stock-value-change/stock-value-change.service';
import { StockValueChange } from 'app/shared/model/stock-value-change.model';

describe('Component Tests', () => {
  describe('StockValueChange Management Component', () => {
    let comp: StockValueChangeComponent;
    let fixture: ComponentFixture<StockValueChangeComponent>;
    let service: StockValueChangeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [StockValueChangeComponent],
        providers: []
      })
        .overrideTemplate(StockValueChangeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StockValueChangeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StockValueChangeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new StockValueChange(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.stockValueChanges && comp.stockValueChanges[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
