import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CasinoTestModule } from '../../../test.module';
import { AktieDetailComponent } from 'app/entities/aktie/aktie-detail.component';
import { Aktie } from 'app/shared/model/aktie.model';

describe('Component Tests', () => {
  describe('Aktie Management Detail Component', () => {
    let comp: AktieDetailComponent;
    let fixture: ComponentFixture<AktieDetailComponent>;
    const route = ({ data: of({ aktie: new Aktie(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [AktieDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AktieDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AktieDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.aktie).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
