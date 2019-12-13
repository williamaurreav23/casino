import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CasinoTestModule } from '../../../test.module';
import { SpielerAktieDetailComponent } from 'app/entities/spieler-aktie/spieler-aktie-detail.component';
import { SpielerAktie } from 'app/shared/model/spieler-aktie.model';

describe('Component Tests', () => {
  describe('SpielerAktie Management Detail Component', () => {
    let comp: SpielerAktieDetailComponent;
    let fixture: ComponentFixture<SpielerAktieDetailComponent>;
    const route = ({ data: of({ spielerAktie: new SpielerAktie(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [SpielerAktieDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SpielerAktieDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SpielerAktieDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.spielerAktie).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
