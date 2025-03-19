import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRistourne, Ristourne } from 'app/shared/model/Finance/ristourne.model';
import { RistourneService } from './ristourne.service';
import { RistourneComponent } from './ristourne.component';
import { RistourneDetailComponent } from './ristourne-detail.component';
import { RistourneUpdateComponent } from './ristourne-update.component';

@Injectable({ providedIn: 'root' })
export class RistourneResolve implements Resolve<IRistourne> {
  constructor(private service: RistourneService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRistourne> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ristourne: HttpResponse<Ristourne>) => {
          if (ristourne.body) {
            return of(ristourne.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Ristourne());
  }
}

export const ristourneRoute: Routes = [
  {
    path: '',
    component: RistourneComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'tsGatewayAngApp.financeRistourne.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RistourneDetailComponent,
    resolve: {
      ristourne: RistourneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.financeRistourne.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RistourneUpdateComponent,
    resolve: {
      ristourne: RistourneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.financeRistourne.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RistourneUpdateComponent,
    resolve: {
      ristourne: RistourneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.financeRistourne.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
