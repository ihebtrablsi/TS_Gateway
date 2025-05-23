import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TsGatewayAngTestModule } from '../../../../test.module';
import { PointDeVenteDetailComponent } from 'app/entities/ClientSales/point-de-vente/point-de-vente-detail.component';
import { PointDeVente } from 'app/shared/model/ClientSales/point-de-vente.model';

describe('Component Tests', () => {
  describe('PointDeVente Management Detail Component', () => {
    let comp: PointDeVenteDetailComponent;
    let fixture: ComponentFixture<PointDeVenteDetailComponent>;
    const route = ({ data: of({ pointDeVente: new PointDeVente(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TsGatewayAngTestModule],
        declarations: [PointDeVenteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PointDeVenteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PointDeVenteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pointDeVente on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pointDeVente).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
