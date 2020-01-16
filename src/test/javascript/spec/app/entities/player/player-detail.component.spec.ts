import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CasinoTestModule } from '../../../test.module';
import { PlayerDetailComponent } from 'app/entities/player/player-detail.component';
import { Player } from 'app/shared/model/player.model';

describe('Component Tests', () => {
  describe('Player Management Detail Component', () => {
    let comp: PlayerDetailComponent;
    let fixture: ComponentFixture<PlayerDetailComponent>;
    const route = ({ data: of({ player: new Player(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [PlayerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PlayerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlayerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load player on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.player).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
