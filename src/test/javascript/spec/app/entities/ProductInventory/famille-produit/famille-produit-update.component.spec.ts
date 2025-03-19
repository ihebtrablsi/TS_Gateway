import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TsGatewayAngTestModule } from '../../../../test.module';
import { FamilleProduitUpdateComponent } from 'app/entities/ProductInventory/famille-produit/famille-produit-update.component';
import { FamilleProduitService } from 'app/entities/ProductInventory/famille-produit/famille-produit.service';
import { FamilleProduit } from 'app/shared/model/ProductInventory/famille-produit.model';

describe('Component Tests', () => {
  describe('FamilleProduit Management Update Component', () => {
    let comp: FamilleProduitUpdateComponent;
    let fixture: ComponentFixture<FamilleProduitUpdateComponent>;
    let service: FamilleProduitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TsGatewayAngTestModule],
        declarations: [FamilleProduitUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(FamilleProduitUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FamilleProduitUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FamilleProduitService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FamilleProduit(123);
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
        const entity = new FamilleProduit();
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
