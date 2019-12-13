import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CasinoTestModule } from '../../../test.module';
import { SpielerTransaktionDetailComponent } from 'app/entities/spieler-transaktion/spieler-transaktion-detail.component';
import { SpielerTransaktion } from 'app/shared/model/spieler-transaktion.model';

describe('Component Tests', () => {
  describe('SpielerTransaktion Management Detail Component', () => {
    let comp: SpielerTransaktionDetailComponent;
    let fixture: ComponentFixture<SpielerTransaktionDetailComponent>;
    const route = ({ data: of({ spielerTransaktion: new SpielerTransaktion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [SpielerTransaktionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SpielerTransaktionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SpielerTransaktionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.spielerTransaktion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
