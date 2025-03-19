import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICode, Code } from 'app/shared/model/ProductInventory/code.model';
import { CodeService } from './code.service';
import { CodeComponent } from './code.component';
import { CodeDetailComponent } from './code-detail.component';
import { CodeUpdateComponent } from './code-update.component';

@Injectable({ providedIn: 'root' })
export class CodeResolve implements Resolve<ICode> {
  constructor(private service: CodeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICode> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((code: HttpResponse<Code>) => {
          if (code.body) {
            return of(code.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Code());
  }
}

export const codeRoute: Routes = [
  {
    path: '',
    component: CodeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'tsGatewayAngApp.productInventoryCode.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CodeDetailComponent,
    resolve: {
      code: CodeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.productInventoryCode.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CodeUpdateComponent,
    resolve: {
      code: CodeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.productInventoryCode.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CodeUpdateComponent,
    resolve: {
      code: CodeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.productInventoryCode.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
