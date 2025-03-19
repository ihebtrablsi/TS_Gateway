import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEcartStock, EcartStock } from 'app/shared/model/ProductInventory/ecart-stock.model';
import { EcartStockService } from './ecart-stock.service';
import { EcartStockComponent } from './ecart-stock.component';
import { EcartStockDetailComponent } from './ecart-stock-detail.component';
import { EcartStockUpdateComponent } from './ecart-stock-update.component';

@Injectable({ providedIn: 'root' })
export class EcartStockResolve implements Resolve<IEcartStock> {
  constructor(private service: EcartStockService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEcartStock> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((ecartStock: HttpResponse<EcartStock>) => {
          if (ecartStock.body) {
            return of(ecartStock.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EcartStock());
  }
}

export const ecartStockRoute: Routes = [
  {
    path: '',
    component: EcartStockComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'tsGatewayAngApp.productInventoryEcartStock.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EcartStockDetailComponent,
    resolve: {
      ecartStock: EcartStockResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.productInventoryEcartStock.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EcartStockUpdateComponent,
    resolve: {
      ecartStock: EcartStockResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.productInventoryEcartStock.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EcartStockUpdateComponent,
    resolve: {
      ecartStock: EcartStockResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.productInventoryEcartStock.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
