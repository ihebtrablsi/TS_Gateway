import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TsGatewayAngTestModule } from '../../../../test.module';
import { DepotDetailComponent } from 'app/entities/ProductInventory/depot/depot-detail.component';
import { Depot } from 'app/shared/model/ProductInventory/depot.model';

describe('Component Tests', () => {
  describe('Depot Management Detail Component', () => {
    let comp: DepotDetailComponent;
    let fixture: ComponentFixture<DepotDetailComponent>;
    const route = ({ data: of({ depot: new Depot(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TsGatewayAngTestModule],
        declarations: [DepotDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DepotDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DepotDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load depot on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.depot).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
