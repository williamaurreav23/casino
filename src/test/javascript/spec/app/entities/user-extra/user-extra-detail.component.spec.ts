import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CasinoTestModule } from '../../../test.module';
import { UserExtraDetailComponent } from 'app/entities/user-extra/user-extra-detail.component';
import { UserExtra } from 'app/shared/model/user-extra.model';

describe('Component Tests', () => {
  describe('UserExtra Management Detail Component', () => {
    let comp: UserExtraDetailComponent;
    let fixture: ComponentFixture<UserExtraDetailComponent>;
    const route = ({ data: of({ userExtra: new UserExtra(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasinoTestModule],
        declarations: [UserExtraDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserExtraDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserExtraDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userExtra on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userExtra).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
