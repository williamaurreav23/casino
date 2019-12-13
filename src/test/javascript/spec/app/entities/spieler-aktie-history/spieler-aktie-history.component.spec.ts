import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CasinoTestModule } from '../../../test.module';
import { SpielerAktieHistoryComponent } from 'app/entities/spieler-aktie-history/spieler-aktie-history.component';
import { SpielerAktieHistoryService } from 'app/entities/spieler-aktie-history/spieler-aktie-history.service';
import { SpielerAktieHistory } from 'app/shared/model/spieler-aktie-history.model';

describe('Component Tests', () => {
  describe('SpielerAktieHistory Management Component', () => {
    let comp: SpielerAktieHistoryComponent;
    let fixture: ComponentFixture<SpielerAktieHistoryComponent>;
    let service: SpielerAktieHistoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [SpielerAktieHistoryComponent],
        providers: []
      })
        .overrideTemplate(SpielerAktieHistoryComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SpielerAktieHistoryComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpielerAktieHistoryService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SpielerAktieHistory(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.spielerAktieHistories[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
