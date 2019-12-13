import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CasinoTestModule } from '../../../test.module';
import { AktieWertComponent } from 'app/entities/aktie-wert/aktie-wert.component';
import { AktieWertService } from 'app/entities/aktie-wert/aktie-wert.service';
import { AktieWert } from 'app/shared/model/aktie-wert.model';

describe('Component Tests', () => {
  describe('AktieWert Management Component', () => {
    let comp: AktieWertComponent;
    let fixture: ComponentFixture<AktieWertComponent>;
    let service: AktieWertService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [AktieWertComponent],
        providers: []
      })
        .overrideTemplate(AktieWertComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AktieWertComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AktieWertService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AktieWert(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.aktieWerts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
