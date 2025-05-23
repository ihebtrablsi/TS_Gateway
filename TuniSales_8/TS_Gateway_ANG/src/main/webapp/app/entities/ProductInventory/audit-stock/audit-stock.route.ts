import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAuditStock, AuditStock } from 'app/shared/model/ProductInventory/audit-stock.model';
import { AuditStockService } from './audit-stock.service';
import { AuditStockComponent } from './audit-stock.component';
import { AuditStockDetailComponent } from './audit-stock-detail.component';
import { AuditStockUpdateComponent } from './audit-stock-update.component';

@Injectable({ providedIn: 'root' })
export class AuditStockResolve implements Resolve<IAuditStock> {
  constructor(private service: AuditStockService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAuditStock> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((auditStock: HttpResponse<AuditStock>) => {
          if (auditStock.body) {
            return of(auditStock.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AuditStock());
  }
}

export const auditStockRoute: Routes = [
  {
    path: '',
    component: AuditStockComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'tsGatewayAngApp.productInventoryAuditStock.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AuditStockDetailComponent,
    resolve: {
      auditStock: AuditStockResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.productInventoryAuditStock.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AuditStockUpdateComponent,
    resolve: {
      auditStock: AuditStockResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.productInventoryAuditStock.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AuditStockUpdateComponent,
    resolve: {
      auditStock: AuditStockResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.productInventoryAuditStock.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
