import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TsGatewayAngTestModule } from '../../../../test.module';
import { RistourneUpdateComponent } from 'app/entities/Finance/ristourne/ristourne-update.component';
import { RistourneService } from 'app/entities/Finance/ristourne/ristourne.service';
import { Ristourne } from 'app/shared/model/Finance/ristourne.model';

describe('Component Tests', () => {
  describe('Ristourne Management Update Component', () => {
    let comp: RistourneUpdateComponent;
    let fixture: ComponentFixture<RistourneUpdateComponent>;
    let service: RistourneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TsGatewayAngTestModule],
        declarations: [RistourneUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RistourneUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RistourneUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RistourneService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Ristourne(123);
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
        const entity = new Ristourne();
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
