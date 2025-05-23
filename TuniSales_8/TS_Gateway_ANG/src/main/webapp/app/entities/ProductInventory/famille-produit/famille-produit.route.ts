import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFamilleProduit, FamilleProduit } from 'app/shared/model/ProductInventory/famille-produit.model';
import { FamilleProduitService } from './famille-produit.service';
import { FamilleProduitComponent } from './famille-produit.component';
import { FamilleProduitDetailComponent } from './famille-produit-detail.component';
import { FamilleProduitUpdateComponent } from './famille-produit-update.component';

@Injectable({ providedIn: 'root' })
export class FamilleProduitResolve implements Resolve<IFamilleProduit> {
  constructor(private service: FamilleProduitService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFamilleProduit> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((familleProduit: HttpResponse<FamilleProduit>) => {
          if (familleProduit.body) {
            return of(familleProduit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FamilleProduit());
  }
}

export const familleProduitRoute: Routes = [
  {
    path: '',
    component: FamilleProduitComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'tsGatewayAngApp.productInventoryFamilleProduit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FamilleProduitDetailComponent,
    resolve: {
      familleProduit: FamilleProduitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.productInventoryFamilleProduit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FamilleProduitUpdateComponent,
    resolve: {
      familleProduit: FamilleProduitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.productInventoryFamilleProduit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FamilleProduitUpdateComponent,
    resolve: {
      familleProduit: FamilleProduitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.productInventoryFamilleProduit.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
