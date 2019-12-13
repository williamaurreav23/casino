import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CasinoTestModule } from '../../../test.module';
import { SpielerComponent } from 'app/entities/spieler/spieler.component';
import { SpielerService } from 'app/entities/spieler/spieler.service';
import { Spieler } from 'app/shared/model/spieler.model';

describe('Component Tests', () => {
  describe('Spieler Management Component', () => {
    let comp: SpielerComponent;
    let fixture: ComponentFixture<SpielerComponent>;
    let service: SpielerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [SpielerComponent],
        providers: []
      })
        .overrideTemplate(SpielerComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SpielerComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpielerService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Spieler(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.spielers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
