import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TsGatewayAngTestModule } from '../../../../test.module';
import { EcartStockDetailComponent } from 'app/entities/ProductInventory/ecart-stock/ecart-stock-detail.component';
import { EcartStock } from 'app/shared/model/ProductInventory/ecart-stock.model';

describe('Component Tests', () => {
  describe('EcartStock Management Detail Component', () => {
    let comp: EcartStockDetailComponent;
    let fixture: ComponentFixture<EcartStockDetailComponent>;
    const route = ({ data: of({ ecartStock: new EcartStock(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TsGatewayAngTestModule],
        declarations: [EcartStockDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EcartStockDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EcartStockDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ecartStock on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ecartStock).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
