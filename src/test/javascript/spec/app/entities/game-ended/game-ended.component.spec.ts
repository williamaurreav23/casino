import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CasinoTestModule } from '../../../test.module';
import { GameEndedComponent } from 'app/entities/game-ended/game-ended.component';
import { GameEndedService } from 'app/entities/game-ended/game-ended.service';
import { GameEnded } from 'app/shared/model/game-ended.model';

describe('Component Tests', () => {
  describe('GameEnded Management Component', () => {
    let comp: GameEndedComponent;
    let fixture: ComponentFixture<GameEndedComponent>;
    let service: GameEndedService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [GameEndedComponent],
        providers: []
      })
        .overrideTemplate(GameEndedComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GameEndedComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GameEndedService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GameEnded(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.gameEndeds && comp.gameEndeds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
