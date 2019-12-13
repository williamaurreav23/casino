import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CasinoTestModule } from '../../../test.module';
import { AktieComponent } from 'app/entities/aktie/aktie.component';
import { AktieService } from 'app/entities/aktie/aktie.service';
import { Aktie } from 'app/shared/model/aktie.model';

describe('Component Tests', () => {
  describe('Aktie Management Component', () => {
    let comp: AktieComponent;
    let fixture: ComponentFixture<AktieComponent>;
    let service: AktieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [AktieComponent],
        providers: []
      })
        .overrideTemplate(AktieComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AktieComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AktieService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Aktie(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.akties[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
