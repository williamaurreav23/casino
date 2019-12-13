import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CasinoTestModule } from '../../../test.module';
import { SpielerAktieHistoryDetailComponent } from 'app/entities/spieler-aktie-history/spieler-aktie-history-detail.component';
import { SpielerAktieHistory } from 'app/shared/model/spieler-aktie-history.model';

describe('Component Tests', () => {
  describe('SpielerAktieHistory Management Detail Component', () => {
    let comp: SpielerAktieHistoryDetailComponent;
    let fixture: ComponentFixture<SpielerAktieHistoryDetailComponent>;
    const route = ({ data: of({ spielerAktieHistory: new SpielerAktieHistory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [SpielerAktieHistoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SpielerAktieHistoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SpielerAktieHistoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.spielerAktieHistory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
