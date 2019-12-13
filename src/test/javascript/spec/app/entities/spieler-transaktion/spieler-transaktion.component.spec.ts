import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CasinoTestModule } from '../../../test.module';
import { SpielerTransaktionComponent } from 'app/entities/spieler-transaktion/spieler-transaktion.component';
import { SpielerTransaktionService } from 'app/entities/spieler-transaktion/spieler-transaktion.service';
import { SpielerTransaktion } from 'app/shared/model/spieler-transaktion.model';

describe('Component Tests', () => {
  describe('SpielerTransaktion Management Component', () => {
    let comp: SpielerTransaktionComponent;
    let fixture: ComponentFixture<SpielerTransaktionComponent>;
    let service: SpielerTransaktionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [SpielerTransaktionComponent],
        providers: []
      })
        .overrideTemplate(SpielerTransaktionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SpielerTransaktionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpielerTransaktionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SpielerTransaktion(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.spielerTransaktions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
