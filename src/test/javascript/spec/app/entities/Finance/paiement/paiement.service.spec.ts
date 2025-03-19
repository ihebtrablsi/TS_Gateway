import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PaiementService } from 'app/entities/Finance/paiement/paiement.service';
import { IPaiement, Paiement } from 'app/shared/model/Finance/paiement.model';

describe('Service Tests', () => {
  describe('Paiement Service', () => {
    let injector: TestBed;
    let service: PaiementService;
    let httpMock: HttpTestingController;
    let elemDefault: IPaiement;
    let expectedResult: IPaiement | IPaiement[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PaiementService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Paiement(0, 'AAAAAAA', currentDate, 0, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            datePaiement: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Paiement', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            datePaiement: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datePaiement: currentDate,
          },
          returnedFromService
        );

        service.create(new Paiement()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Paiement', () => {
        const returnedFromService = Object.assign(
          {
            reference: 'BBBBBB',
            datePaiement: currentDate.format(DATE_TIME_FORMAT),
            montant: 1,
            modePaiement: 'BBBBBB',
            statut: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datePaiement: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Paiement', () => {
        const returnedFromService = Object.assign(
          {
            reference: 'BBBBBB',
            datePaiement: currentDate.format(DATE_TIME_FORMAT),
            montant: 1,
            modePaiement: 'BBBBBB',
            statut: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            datePaiement: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Paiement', () => {
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
