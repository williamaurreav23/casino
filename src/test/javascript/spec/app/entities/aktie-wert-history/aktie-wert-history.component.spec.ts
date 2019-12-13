import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CasinoTestModule } from '../../../test.module';
import { AktieWertHistoryComponent } from 'app/entities/aktie-wert-history/aktie-wert-history.component';
import { AktieWertHistoryService } from 'app/entities/aktie-wert-history/aktie-wert-history.service';
import { AktieWertHistory } from 'app/shared/model/aktie-wert-history.model';

describe('Component Tests', () => {
  describe('AktieWertHistory Management Component', () => {
    let comp: AktieWertHistoryComponent;
    let fixture: ComponentFixture<AktieWertHistoryComponent>;
    let service: AktieWertHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [AktieWertHistoryComponent],
        providers: []
      })
        .overrideTemplate(AktieWertHistoryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AktieWertHistoryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AktieWertHistoryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AktieWertHistory(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.aktieWertHistories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
