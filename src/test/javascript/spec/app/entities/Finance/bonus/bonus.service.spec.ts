import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { BonusService } from 'app/entities/Finance/bonus/bonus.service';
import { IBonus, Bonus } from 'app/shared/model/Finance/bonus.model';

describe('Service Tests', () => {
  describe('Bonus Service', () => {
    let injector: TestBed;
    let service: BonusService;
    let httpMock: HttpTestingController;
    let elemDefault: IBonus;
    let expectedResult: IBonus | IBonus[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BonusService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Bonus(0, 0, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateAttribution: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Bonus', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateAttribution: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateAttribution: currentDate,
          },
          returnedFromService
        );

        service.create(new Bonus()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Bonus', () => {
        const returnedFromService = Object.assign(
          {
            montant: 1,
            dateAttribution: currentDate.format(DATE_TIME_FORMAT),
            statut: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateAttribution: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Bonus', () => {
        const returnedFromService = Object.assign(
          {
            montant: 1,
            dateAttribution: currentDate.format(DATE_TIME_FORMAT),
            statut: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateAttribution: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Bonus', () => {
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
