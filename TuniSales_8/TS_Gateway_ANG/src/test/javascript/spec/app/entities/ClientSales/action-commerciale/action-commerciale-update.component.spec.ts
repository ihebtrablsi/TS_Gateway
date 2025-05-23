import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TsGatewayAngTestModule } from '../../../../test.module';
import { ActionCommercialeUpdateComponent } from 'app/entities/ClientSales/action-commerciale/action-commerciale-update.component';
import { ActionCommercialeService } from 'app/entities/ClientSales/action-commerciale/action-commerciale.service';
import { ActionCommerciale } from 'app/shared/model/ClientSales/action-commerciale.model';

describe('Component Tests', () => {
  describe('ActionCommerciale Management Update Component', () => {
    let comp: ActionCommercialeUpdateComponent;
    let fixture: ComponentFixture<ActionCommercialeUpdateComponent>;
    let service: ActionCommercialeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TsGatewayAngTestModule],
        declarations: [ActionCommercialeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ActionCommercialeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ActionCommercialeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ActionCommercialeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ActionCommerciale(123);
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
        const entity = new ActionCommerciale();
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
