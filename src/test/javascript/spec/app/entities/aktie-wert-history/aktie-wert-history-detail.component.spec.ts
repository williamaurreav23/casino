import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CasinoTestModule } from '../../../test.module';
import { AktieWertHistoryDetailComponent } from 'app/entities/aktie-wert-history/aktie-wert-history-detail.component';
import { AktieWertHistory } from 'app/shared/model/aktie-wert-history.model';

describe('Component Tests', () => {
  describe('AktieWertHistory Management Detail Component', () => {
    let comp: AktieWertHistoryDetailComponent;
    let fixture: ComponentFixture<AktieWertHistoryDetailComponent>;
    const route = ({ data: of({ aktieWertHistory: new AktieWertHistory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [AktieWertHistoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AktieWertHistoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AktieWertHistoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.aktieWertHistory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
