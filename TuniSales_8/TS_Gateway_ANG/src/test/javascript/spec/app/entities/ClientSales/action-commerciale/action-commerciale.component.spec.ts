import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { TsGatewayAngTestModule } from '../../../../test.module';
import { ActionCommercialeComponent } from 'app/entities/ClientSales/action-commerciale/action-commerciale.component';
import { ActionCommercialeService } from 'app/entities/ClientSales/action-commerciale/action-commerciale.service';
import { ActionCommerciale } from 'app/shared/model/ClientSales/action-commerciale.model';

describe('Component Tests', () => {
  describe('ActionCommerciale Management Component', () => {
    let comp: ActionCommercialeComponent;
    let fixture: ComponentFixture<ActionCommercialeComponent>;
    let service: ActionCommercialeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TsGatewayAngTestModule],
        declarations: [ActionCommercialeComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(ActionCommercialeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ActionCommercialeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ActionCommercialeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ActionCommerciale(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.actionCommerciales && comp.actionCommerciales[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ActionCommerciale(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.actionCommerciales && comp.actionCommerciales[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
