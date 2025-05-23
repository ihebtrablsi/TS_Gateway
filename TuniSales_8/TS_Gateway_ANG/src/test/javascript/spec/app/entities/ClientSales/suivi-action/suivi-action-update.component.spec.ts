import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TsGatewayAngTestModule } from '../../../../test.module';
import { SuiviActionUpdateComponent } from 'app/entities/ClientSales/suivi-action/suivi-action-update.component';
import { SuiviActionService } from 'app/entities/ClientSales/suivi-action/suivi-action.service';
import { SuiviAction } from 'app/shared/model/ClientSales/suivi-action.model';

describe('Component Tests', () => {
  describe('SuiviAction Management Update Component', () => {
    let comp: SuiviActionUpdateComponent;
    let fixture: ComponentFixture<SuiviActionUpdateComponent>;
    let service: SuiviActionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TsGatewayAngTestModule],
        declarations: [SuiviActionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SuiviActionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SuiviActionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SuiviActionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SuiviAction(123);
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
        const entity = new SuiviAction();
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
