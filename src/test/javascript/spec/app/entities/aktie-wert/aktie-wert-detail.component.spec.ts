import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CasinoTestModule } from '../../../test.module';
import { AktieWertDetailComponent } from 'app/entities/aktie-wert/aktie-wert-detail.component';
import { AktieWert } from 'app/shared/model/aktie-wert.model';

describe('Component Tests', () => {
  describe('AktieWert Management Detail Component', () => {
    let comp: AktieWertDetailComponent;
    let fixture: ComponentFixture<AktieWertDetailComponent>;
    const route = ({ data: of({ aktieWert: new AktieWert(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [AktieWertDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AktieWertDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AktieWertDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.aktieWert).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
