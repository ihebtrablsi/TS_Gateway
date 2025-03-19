import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TsGatewayAngTestModule } from '../../../../test.module';
import { PointDeVenteUpdateComponent } from 'app/entities/ClientSales/point-de-vente/point-de-vente-update.component';
import { PointDeVenteService } from 'app/entities/ClientSales/point-de-vente/point-de-vente.service';
import { PointDeVente } from 'app/shared/model/ClientSales/point-de-vente.model';

describe('Component Tests', () => {
  describe('PointDeVente Management Update Component', () => {
    let comp: PointDeVenteUpdateComponent;
    let fixture: ComponentFixture<PointDeVenteUpdateComponent>;
    let service: PointDeVenteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TsGatewayAngTestModule],
        declarations: [PointDeVenteUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PointDeVenteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PointDeVenteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PointDeVenteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PointDeVente(123);
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
        const entity = new PointDeVente();
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
