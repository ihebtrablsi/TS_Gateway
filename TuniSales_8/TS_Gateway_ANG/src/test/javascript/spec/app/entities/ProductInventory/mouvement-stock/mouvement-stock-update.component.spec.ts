import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TsGatewayAngTestModule } from '../../../../test.module';
import { MouvementStockUpdateComponent } from 'app/entities/ProductInventory/mouvement-stock/mouvement-stock-update.component';
import { MouvementStockService } from 'app/entities/ProductInventory/mouvement-stock/mouvement-stock.service';
import { MouvementStock } from 'app/shared/model/ProductInventory/mouvement-stock.model';

describe('Component Tests', () => {
  describe('MouvementStock Management Update Component', () => {
    let comp: MouvementStockUpdateComponent;
    let fixture: ComponentFixture<MouvementStockUpdateComponent>;
    let service: MouvementStockService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TsGatewayAngTestModule],
        declarations: [MouvementStockUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MouvementStockUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MouvementStockUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MouvementStockService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MouvementStock(123);
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
        const entity = new MouvementStock();
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
