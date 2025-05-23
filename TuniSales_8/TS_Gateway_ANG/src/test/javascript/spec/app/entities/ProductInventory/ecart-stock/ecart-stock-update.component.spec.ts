import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TsGatewayAngTestModule } from '../../../../test.module';
import { EcartStockUpdateComponent } from 'app/entities/ProductInventory/ecart-stock/ecart-stock-update.component';
import { EcartStockService } from 'app/entities/ProductInventory/ecart-stock/ecart-stock.service';
import { EcartStock } from 'app/shared/model/ProductInventory/ecart-stock.model';

describe('Component Tests', () => {
  describe('EcartStock Management Update Component', () => {
    let comp: EcartStockUpdateComponent;
    let fixture: ComponentFixture<EcartStockUpdateComponent>;
    let service: EcartStockService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TsGatewayAngTestModule],
        declarations: [EcartStockUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EcartStockUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EcartStockUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EcartStockService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EcartStock(123);
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
        const entity = new EcartStock();
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
