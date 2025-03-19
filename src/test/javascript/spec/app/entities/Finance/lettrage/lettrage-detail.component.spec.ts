import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TsGatewayAngTestModule } from '../../../../test.module';
import { LettrageDetailComponent } from 'app/entities/Finance/lettrage/lettrage-detail.component';
import { Lettrage } from 'app/shared/model/Finance/lettrage.model';

describe('Component Tests', () => {
  describe('Lettrage Management Detail Component', () => {
    let comp: LettrageDetailComponent;
    let fixture: ComponentFixture<LettrageDetailComponent>;
    const route = ({ data: of({ lettrage: new Lettrage(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TsGatewayAngTestModule],
        declarations: [LettrageDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LettrageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LettrageDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load lettrage on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.lettrage).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
