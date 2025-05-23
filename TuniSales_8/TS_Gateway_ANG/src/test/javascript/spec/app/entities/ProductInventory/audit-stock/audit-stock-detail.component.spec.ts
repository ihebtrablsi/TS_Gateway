import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TsGatewayAngTestModule } from '../../../../test.module';
import { AuditStockDetailComponent } from 'app/entities/ProductInventory/audit-stock/audit-stock-detail.component';
import { AuditStock } from 'app/shared/model/ProductInventory/audit-stock.model';

describe('Component Tests', () => {
  describe('AuditStock Management Detail Component', () => {
    let comp: AuditStockDetailComponent;
    let fixture: ComponentFixture<AuditStockDetailComponent>;
    const route = ({ data: of({ auditStock: new AuditStock(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TsGatewayAngTestModule],
        declarations: [AuditStockDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AuditStockDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AuditStockDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load auditStock on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.auditStock).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
