import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TsGatewayAngTestModule } from '../../../../test.module';
import { CodeDetailComponent } from 'app/entities/ProductInventory/code/code-detail.component';
import { Code } from 'app/shared/model/ProductInventory/code.model';

describe('Component Tests', () => {
  describe('Code Management Detail Component', () => {
    let comp: CodeDetailComponent;
    let fixture: ComponentFixture<CodeDetailComponent>;
    const route = ({ data: of({ code: new Code(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TsGatewayAngTestModule],
        declarations: [CodeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CodeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CodeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load code on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.code).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
