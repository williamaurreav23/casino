import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CasinoTestModule } from '../../../test.module';
import { SpielerDetailComponent } from 'app/entities/spieler/spieler-detail.component';
import { Spieler } from 'app/shared/model/spieler.model';

describe('Component Tests', () => {
  describe('Spieler Management Detail Component', () => {
    let comp: SpielerDetailComponent;
    let fixture: ComponentFixture<SpielerDetailComponent>;
    const route = ({ data: of({ spieler: new Spieler(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [SpielerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SpielerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SpielerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.spieler).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
