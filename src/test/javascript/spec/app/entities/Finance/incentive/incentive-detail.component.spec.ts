import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TsGatewayAngTestModule } from '../../../../test.module';
import { IncentiveDetailComponent } from 'app/entities/Finance/incentive/incentive-detail.component';
import { Incentive } from 'app/shared/model/Finance/incentive.model';

describe('Component Tests', () => {
  describe('Incentive Management Detail Component', () => {
    let comp: IncentiveDetailComponent;
    let fixture: ComponentFixture<IncentiveDetailComponent>;
    const route = ({ data: of({ incentive: new Incentive(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TsGatewayAngTestModule],
        declarations: [IncentiveDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(IncentiveDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IncentiveDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load incentive on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.incentive).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
