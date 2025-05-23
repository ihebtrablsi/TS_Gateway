import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { SuiviActionService } from 'app/entities/ClientSales/suivi-action/suivi-action.service';
import { ISuiviAction, SuiviAction } from 'app/shared/model/ClientSales/suivi-action.model';

describe('Service Tests', () => {
  describe('SuiviAction Service', () => {
    let injector: TestBed;
    let service: SuiviActionService;
    let httpMock: HttpTestingController;
    let elemDefault: ISuiviAction;
    let expectedResult: ISuiviAction | ISuiviAction[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SuiviActionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new SuiviAction(0, currentDate, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateSuivi: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SuiviAction', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateSuivi: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateSuivi: currentDate,
          },
          returnedFromService
        );

        service.create(new SuiviAction()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SuiviAction', () => {
        const returnedFromService = Object.assign(
          {
            dateSuivi: currentDate.format(DATE_TIME_FORMAT),
            indicateurs: 'BBBBBB',
            commentaire: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateSuivi: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SuiviAction', () => {
        const returnedFromService = Object.assign(
          {
            dateSuivi: currentDate.format(DATE_TIME_FORMAT),
            indicateurs: 'BBBBBB',
            commentaire: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateSuivi: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a SuiviAction', () => {
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
