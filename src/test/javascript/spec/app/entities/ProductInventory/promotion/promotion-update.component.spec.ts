import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TsGatewayAngTestModule } from '../../../../test.module';
import { PromotionUpdateComponent } from 'app/entities/ProductInventory/promotion/promotion-update.component';
import { PromotionService } from 'app/entities/ProductInventory/promotion/promotion.service';
import { Promotion } from 'app/shared/model/ProductInventory/promotion.model';

describe('Component Tests', () => {
  describe('Promotion Management Update Component', () => {
    let comp: PromotionUpdateComponent;
    let fixture: ComponentFixture<PromotionUpdateComponent>;
    let service: PromotionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TsGatewayAngTestModule],
        declarations: [PromotionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PromotionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PromotionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PromotionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Promotion(123);
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
        const entity = new Promotion();
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
