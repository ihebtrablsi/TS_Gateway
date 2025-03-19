import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TsGatewayAngTestModule } from '../../../../test.module';
import { CodeUpdateComponent } from 'app/entities/ProductInventory/code/code-update.component';
import { CodeService } from 'app/entities/ProductInventory/code/code.service';
import { Code } from 'app/shared/model/ProductInventory/code.model';

describe('Component Tests', () => {
  describe('Code Management Update Component', () => {
    let comp: CodeUpdateComponent;
    let fixture: ComponentFixture<CodeUpdateComponent>;
    let service: CodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TsGatewayAngTestModule],
        declarations: [CodeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CodeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CodeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CodeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Code(123);
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
        const entity = new Code();
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
