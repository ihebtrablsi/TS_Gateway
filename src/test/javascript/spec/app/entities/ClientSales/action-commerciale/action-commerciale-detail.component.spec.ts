import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TsGatewayAngTestModule } from '../../../../test.module';
import { ActionCommercialeDetailComponent } from 'app/entities/ClientSales/action-commerciale/action-commerciale-detail.component';
import { ActionCommerciale } from 'app/shared/model/ClientSales/action-commerciale.model';

describe('Component Tests', () => {
  describe('ActionCommerciale Management Detail Component', () => {
    let comp: ActionCommercialeDetailComponent;
    let fixture: ComponentFixture<ActionCommercialeDetailComponent>;
    const route = ({ data: of({ actionCommerciale: new ActionCommerciale(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TsGatewayAngTestModule],
        declarations: [ActionCommercialeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ActionCommercialeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ActionCommercialeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load actionCommerciale on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.actionCommerciale).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
