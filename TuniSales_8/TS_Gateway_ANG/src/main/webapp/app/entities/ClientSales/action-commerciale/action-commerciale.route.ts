import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IActionCommerciale, ActionCommerciale } from 'app/shared/model/ClientSales/action-commerciale.model';
import { ActionCommercialeService } from './action-commerciale.service';
import { ActionCommercialeComponent } from './action-commerciale.component';
import { ActionCommercialeDetailComponent } from './action-commerciale-detail.component';
import { ActionCommercialeUpdateComponent } from './action-commerciale-update.component';

@Injectable({ providedIn: 'root' })
export class ActionCommercialeResolve implements Resolve<IActionCommerciale> {
  constructor(private service: ActionCommercialeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IActionCommerciale> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((actionCommerciale: HttpResponse<ActionCommerciale>) => {
          if (actionCommerciale.body) {
            return of(actionCommerciale.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ActionCommerciale());
  }
}

export const actionCommercialeRoute: Routes = [
  {
    path: '',
    component: ActionCommercialeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'tsGatewayAngApp.clientSalesActionCommerciale.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ActionCommercialeDetailComponent,
    resolve: {
      actionCommerciale: ActionCommercialeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.clientSalesActionCommerciale.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ActionCommercialeUpdateComponent,
    resolve: {
      actionCommerciale: ActionCommercialeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.clientSalesActionCommerciale.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ActionCommercialeUpdateComponent,
    resolve: {
      actionCommerciale: ActionCommercialeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.clientSalesActionCommerciale.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
