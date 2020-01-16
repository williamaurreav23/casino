import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CasinoTestModule } from '../../../test.module';
import { StockValueChangeDetailComponent } from 'app/entities/stock-value-change/stock-value-change-detail.component';
import { StockValueChange } from 'app/shared/model/stock-value-change.model';

describe('Component Tests', () => {
  describe('StockValueChange Management Detail Component', () => {
    let comp: StockValueChangeDetailComponent;
    let fixture: ComponentFixture<StockValueChangeDetailComponent>;
    const route = ({ data: of({ stockValueChange: new StockValueChange(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [StockValueChangeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(StockValueChangeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StockValueChangeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load stockValueChange on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.stockValueChange).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
