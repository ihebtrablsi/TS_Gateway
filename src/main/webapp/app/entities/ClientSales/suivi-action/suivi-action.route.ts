import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISuiviAction, SuiviAction } from 'app/shared/model/ClientSales/suivi-action.model';
import { SuiviActionService } from './suivi-action.service';
import { SuiviActionComponent } from './suivi-action.component';
import { SuiviActionDetailComponent } from './suivi-action-detail.component';
import { SuiviActionUpdateComponent } from './suivi-action-update.component';

@Injectable({ providedIn: 'root' })
export class SuiviActionResolve implements Resolve<ISuiviAction> {
  constructor(private service: SuiviActionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISuiviAction> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((suiviAction: HttpResponse<SuiviAction>) => {
          if (suiviAction.body) {
            return of(suiviAction.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SuiviAction());
  }
}

export const suiviActionRoute: Routes = [
  {
    path: '',
    component: SuiviActionComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'tsGatewayAngApp.clientSalesSuiviAction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SuiviActionDetailComponent,
    resolve: {
      suiviAction: SuiviActionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.clientSalesSuiviAction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SuiviActionUpdateComponent,
    resolve: {
      suiviAction: SuiviActionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.clientSalesSuiviAction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SuiviActionUpdateComponent,
    resolve: {
      suiviAction: SuiviActionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.clientSalesSuiviAction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
