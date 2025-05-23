import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TsGatewayAngTestModule } from '../../../../test.module';
import { PanierBonusDetailComponent } from 'app/entities/ClientSales/panier-bonus/panier-bonus-detail.component';
import { PanierBonus } from 'app/shared/model/ClientSales/panier-bonus.model';

describe('Component Tests', () => {
  describe('PanierBonus Management Detail Component', () => {
    let comp: PanierBonusDetailComponent;
    let fixture: ComponentFixture<PanierBonusDetailComponent>;
    const route = ({ data: of({ panierBonus: new PanierBonus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TsGatewayAngTestModule],
        declarations: [PanierBonusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PanierBonusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PanierBonusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load panierBonus on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.panierBonus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
