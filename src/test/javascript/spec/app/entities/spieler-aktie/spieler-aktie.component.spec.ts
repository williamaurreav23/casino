import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CasinoTestModule } from '../../../test.module';
import { SpielerAktieComponent } from 'app/entities/spieler-aktie/spieler-aktie.component';
import { SpielerAktieService } from 'app/entities/spieler-aktie/spieler-aktie.service';
import { SpielerAktie } from 'app/shared/model/spieler-aktie.model';

describe('Component Tests', () => {
  describe('SpielerAktie Management Component', () => {
    let comp: SpielerAktieComponent;
    let fixture: ComponentFixture<SpielerAktieComponent>;
    let service: SpielerAktieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [SpielerAktieComponent],
        providers: []
      })
        .overrideTemplate(SpielerAktieComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SpielerAktieComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpielerAktieService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SpielerAktie(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.spielerAkties[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
