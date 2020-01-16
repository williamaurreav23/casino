import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CasinoTestModule } from '../../../test.module';
import { GameEndedDetailComponent } from 'app/entities/game-ended/game-ended-detail.component';
import { GameEnded } from 'app/shared/model/game-ended.model';

describe('Component Tests', () => {
  describe('GameEnded Management Detail Component', () => {
    let comp: GameEndedDetailComponent;
    let fixture: ComponentFixture<GameEndedDetailComponent>;
    const route = ({ data: of({ gameEnded: new GameEnded(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [GameEndedDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GameEndedDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GameEndedDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load gameEnded on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.gameEnded).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
