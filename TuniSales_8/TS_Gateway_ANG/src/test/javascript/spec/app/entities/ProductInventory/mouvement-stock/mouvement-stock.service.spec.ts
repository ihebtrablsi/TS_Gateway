import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { MouvementStockService } from 'app/entities/ProductInventory/mouvement-stock/mouvement-stock.service';
import { IMouvementStock, MouvementStock } from 'app/shared/model/ProductInventory/mouvement-stock.model';

describe('Service Tests', () => {
  describe('MouvementStock Service', () => {
    let injector: TestBed;
    let service: MouvementStockService;
    let httpMock: HttpTestingController;
    let elemDefault: IMouvementStock;
    let expectedResult: IMouvementStock | IMouvementStock[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MouvementStockService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new MouvementStock(0, 'AAAAAAA', 0, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateMouvement: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a MouvementStock', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateMouvement: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateMouvement: currentDate,
          },
          returnedFromService
        );

        service.create(new MouvementStock()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MouvementStock', () => {
        const returnedFromService = Object.assign(
          {
            typeMouvement: 'BBBBBB',
            quantite: 1,
            dateMouvement: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateMouvement: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of MouvementStock', () => {
        const returnedFromService = Object.assign(
          {
            typeMouvement: 'BBBBBB',
            quantite: 1,
            dateMouvement: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateMouvement: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a MouvementStock', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
