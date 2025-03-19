import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIncentive, Incentive } from 'app/shared/model/Finance/incentive.model';
import { IncentiveService } from './incentive.service';
import { IncentiveComponent } from './incentive.component';
import { IncentiveDetailComponent } from './incentive-detail.component';
import { IncentiveUpdateComponent } from './incentive-update.component';

@Injectable({ providedIn: 'root' })
export class IncentiveResolve implements Resolve<IIncentive> {
  constructor(private service: IncentiveService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIncentive> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((incentive: HttpResponse<Incentive>) => {
          if (incentive.body) {
            return of(incentive.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Incentive());
  }
}

export const incentiveRoute: Routes = [
  {
    path: '',
    component: IncentiveComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'tsGatewayAngApp.financeIncentive.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: IncentiveDetailComponent,
    resolve: {
      incentive: IncentiveResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.financeIncentive.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: IncentiveUpdateComponent,
    resolve: {
      incentive: IncentiveResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.financeIncentive.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: IncentiveUpdateComponent,
    resolve: {
      incentive: IncentiveResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.financeIncentive.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
