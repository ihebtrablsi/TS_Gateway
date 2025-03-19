import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TsGatewayAngTestModule } from '../../../../test.module';
import { RistourneDetailComponent } from 'app/entities/Finance/ristourne/ristourne-detail.component';
import { Ristourne } from 'app/shared/model/Finance/ristourne.model';

describe('Component Tests', () => {
  describe('Ristourne Management Detail Component', () => {
    let comp: RistourneDetailComponent;
    let fixture: ComponentFixture<RistourneDetailComponent>;
    const route = ({ data: of({ ristourne: new Ristourne(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TsGatewayAngTestModule],
        declarations: [RistourneDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RistourneDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RistourneDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ristourne on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ristourne).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
