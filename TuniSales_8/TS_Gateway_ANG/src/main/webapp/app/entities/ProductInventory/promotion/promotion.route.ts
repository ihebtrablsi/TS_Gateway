import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPromotion, Promotion } from 'app/shared/model/ProductInventory/promotion.model';
import { PromotionService } from './promotion.service';
import { PromotionComponent } from './promotion.component';
import { PromotionDetailComponent } from './promotion-detail.component';
import { PromotionUpdateComponent } from './promotion-update.component';

@Injectable({ providedIn: 'root' })
export class PromotionResolve implements Resolve<IPromotion> {
  constructor(private service: PromotionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPromotion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((promotion: HttpResponse<Promotion>) => {
          if (promotion.body) {
            return of(promotion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Promotion());
  }
}

export const promotionRoute: Routes = [
  {
    path: '',
    component: PromotionComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'tsGatewayAngApp.productInventoryPromotion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PromotionDetailComponent,
    resolve: {
      promotion: PromotionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.productInventoryPromotion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PromotionUpdateComponent,
    resolve: {
      promotion: PromotionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.productInventoryPromotion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PromotionUpdateComponent,
    resolve: {
      promotion: PromotionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.productInventoryPromotion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
