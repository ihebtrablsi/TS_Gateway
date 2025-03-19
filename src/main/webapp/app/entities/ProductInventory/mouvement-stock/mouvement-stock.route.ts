import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMouvementStock, MouvementStock } from 'app/shared/model/ProductInventory/mouvement-stock.model';
import { MouvementStockService } from './mouvement-stock.service';
import { MouvementStockComponent } from './mouvement-stock.component';
import { MouvementStockDetailComponent } from './mouvement-stock-detail.component';
import { MouvementStockUpdateComponent } from './mouvement-stock-update.component';

@Injectable({ providedIn: 'root' })
export class MouvementStockResolve implements Resolve<IMouvementStock> {
  constructor(private service: MouvementStockService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMouvementStock> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((mouvementStock: HttpResponse<MouvementStock>) => {
          if (mouvementStock.body) {
            return of(mouvementStock.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MouvementStock());
  }
}

export const mouvementStockRoute: Routes = [
  {
    path: '',
    component: MouvementStockComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'tsGatewayAngApp.productInventoryMouvementStock.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MouvementStockDetailComponent,
    resolve: {
      mouvementStock: MouvementStockResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.productInventoryMouvementStock.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MouvementStockUpdateComponent,
    resolve: {
      mouvementStock: MouvementStockResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.productInventoryMouvementStock.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MouvementStockUpdateComponent,
    resolve: {
      mouvementStock: MouvementStockResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.productInventoryMouvementStock.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
