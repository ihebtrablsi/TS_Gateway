import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPointDeVente, PointDeVente } from 'app/shared/model/ClientSales/point-de-vente.model';
import { PointDeVenteService } from './point-de-vente.service';
import { PointDeVenteComponent } from './point-de-vente.component';
import { PointDeVenteDetailComponent } from './point-de-vente-detail.component';
import { PointDeVenteUpdateComponent } from './point-de-vente-update.component';

@Injectable({ providedIn: 'root' })
export class PointDeVenteResolve implements Resolve<IPointDeVente> {
  constructor(private service: PointDeVenteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPointDeVente> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pointDeVente: HttpResponse<PointDeVente>) => {
          if (pointDeVente.body) {
            return of(pointDeVente.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PointDeVente());
  }
}

export const pointDeVenteRoute: Routes = [
  {
    path: '',
    component: PointDeVenteComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'tsGatewayAngApp.clientSalesPointDeVente.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PointDeVenteDetailComponent,
    resolve: {
      pointDeVente: PointDeVenteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.clientSalesPointDeVente.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PointDeVenteUpdateComponent,
    resolve: {
      pointDeVente: PointDeVenteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.clientSalesPointDeVente.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PointDeVenteUpdateComponent,
    resolve: {
      pointDeVente: PointDeVenteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.clientSalesPointDeVente.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
