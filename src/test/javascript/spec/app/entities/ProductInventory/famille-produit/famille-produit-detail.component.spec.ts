import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TsGatewayAngTestModule } from '../../../../test.module';
import { FamilleProduitDetailComponent } from 'app/entities/ProductInventory/famille-produit/famille-produit-detail.component';
import { FamilleProduit } from 'app/shared/model/ProductInventory/famille-produit.model';

describe('Component Tests', () => {
  describe('FamilleProduit Management Detail Component', () => {
    let comp: FamilleProduitDetailComponent;
    let fixture: ComponentFixture<FamilleProduitDetailComponent>;
    const route = ({ data: of({ familleProduit: new FamilleProduit(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TsGatewayAngTestModule],
        declarations: [FamilleProduitDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(FamilleProduitDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FamilleProduitDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load familleProduit on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.familleProduit).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
