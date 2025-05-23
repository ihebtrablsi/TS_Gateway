import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { LettrageService } from 'app/entities/Finance/lettrage/lettrage.service';
import { ILettrage, Lettrage } from 'app/shared/model/Finance/lettrage.model';

describe('Service Tests', () => {
  describe('Lettrage Service', () => {
    let injector: TestBed;
    let service: LettrageService;
    let httpMock: HttpTestingController;
    let elemDefault: ILettrage;
    let expectedResult: ILettrage | ILettrage[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(LettrageService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Lettrage(0, currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateLettrage: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Lettrage', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateLettrage: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateLettrage: currentDate,
          },
          returnedFromService
        );

        service.create(new Lettrage()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Lettrage', () => {
        const returnedFromService = Object.assign(
          {
            dateLettrage: currentDate.format(DATE_TIME_FORMAT),
            montantLettre: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateLettrage: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Lettrage', () => {
        const returnedFromService = Object.assign(
          {
            dateLettrage: currentDate.format(DATE_TIME_FORMAT),
            montantLettre: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateLettrage: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Lettrage', () => {
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
