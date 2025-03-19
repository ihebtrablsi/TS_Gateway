import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TsGatewayAngTestModule } from '../../../../test.module';
import { PanierBonusUpdateComponent } from 'app/entities/ClientSales/panier-bonus/panier-bonus-update.component';
import { PanierBonusService } from 'app/entities/ClientSales/panier-bonus/panier-bonus.service';
import { PanierBonus } from 'app/shared/model/ClientSales/panier-bonus.model';

describe('Component Tests', () => {
  describe('PanierBonus Management Update Component', () => {
    let comp: PanierBonusUpdateComponent;
    let fixture: ComponentFixture<PanierBonusUpdateComponent>;
    let service: PanierBonusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TsGatewayAngTestModule],
        declarations: [PanierBonusUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PanierBonusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PanierBonusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PanierBonusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PanierBonus(123);
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
        const entity = new PanierBonus();
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
