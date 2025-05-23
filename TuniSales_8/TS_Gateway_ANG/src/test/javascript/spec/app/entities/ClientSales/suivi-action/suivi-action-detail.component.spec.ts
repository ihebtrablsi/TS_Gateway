import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TsGatewayAngTestModule } from '../../../../test.module';
import { SuiviActionDetailComponent } from 'app/entities/ClientSales/suivi-action/suivi-action-detail.component';
import { SuiviAction } from 'app/shared/model/ClientSales/suivi-action.model';

describe('Component Tests', () => {
  describe('SuiviAction Management Detail Component', () => {
    let comp: SuiviActionDetailComponent;
    let fixture: ComponentFixture<SuiviActionDetailComponent>;
    const route = ({ data: of({ suiviAction: new SuiviAction(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TsGatewayAngTestModule],
        declarations: [SuiviActionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SuiviActionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SuiviActionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load suiviAction on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.suiviAction).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
