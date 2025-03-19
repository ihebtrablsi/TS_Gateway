import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TsGatewayAngTestModule } from '../../../../test.module';
import { AuditStockUpdateComponent } from 'app/entities/ProductInventory/audit-stock/audit-stock-update.component';
import { AuditStockService } from 'app/entities/ProductInventory/audit-stock/audit-stock.service';
import { AuditStock } from 'app/shared/model/ProductInventory/audit-stock.model';

describe('Component Tests', () => {
  describe('AuditStock Management Update Component', () => {
    let comp: AuditStockUpdateComponent;
    let fixture: ComponentFixture<AuditStockUpdateComponent>;
    let service: AuditStockService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TsGatewayAngTestModule],
        declarations: [AuditStockUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AuditStockUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AuditStockUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AuditStockService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AuditStock(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AuditStock();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
