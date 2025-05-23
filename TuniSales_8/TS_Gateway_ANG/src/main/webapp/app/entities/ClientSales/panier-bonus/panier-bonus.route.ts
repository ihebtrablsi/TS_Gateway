import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPanierBonus, PanierBonus } from 'app/shared/model/ClientSales/panier-bonus.model';
import { PanierBonusService } from './panier-bonus.service';
import { PanierBonusComponent } from './panier-bonus.component';
import { PanierBonusDetailComponent } from './panier-bonus-detail.component';
import { PanierBonusUpdateComponent } from './panier-bonus-update.component';

@Injectable({ providedIn: 'root' })
export class PanierBonusResolve implements Resolve<IPanierBonus> {
  constructor(private service: PanierBonusService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPanierBonus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((panierBonus: HttpResponse<PanierBonus>) => {
          if (panierBonus.body) {
            return of(panierBonus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PanierBonus());
  }
}

export const panierBonusRoute: Routes = [
  {
    path: '',
    component: PanierBonusComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'tsGatewayAngApp.clientSalesPanierBonus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PanierBonusDetailComponent,
    resolve: {
      panierBonus: PanierBonusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.clientSalesPanierBonus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PanierBonusUpdateComponent,
    resolve: {
      panierBonus: PanierBonusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.clientSalesPanierBonus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PanierBonusUpdateComponent,
    resolve: {
      panierBonus: PanierBonusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.clientSalesPanierBonus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
