import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILettrage, Lettrage } from 'app/shared/model/Finance/lettrage.model';
import { LettrageService } from './lettrage.service';
import { LettrageComponent } from './lettrage.component';
import { LettrageDetailComponent } from './lettrage-detail.component';
import { LettrageUpdateComponent } from './lettrage-update.component';

@Injectable({ providedIn: 'root' })
export class LettrageResolve implements Resolve<ILettrage> {
  constructor(private service: LettrageService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILettrage> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((lettrage: HttpResponse<Lettrage>) => {
          if (lettrage.body) {
            return of(lettrage.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Lettrage());
  }
}

export const lettrageRoute: Routes = [
  {
    path: '',
    component: LettrageComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'tsGatewayAngApp.financeLettrage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LettrageDetailComponent,
    resolve: {
      lettrage: LettrageResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.financeLettrage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LettrageUpdateComponent,
    resolve: {
      lettrage: LettrageResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.financeLettrage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LettrageUpdateComponent,
    resolve: {
      lettrage: LettrageResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tsGatewayAngApp.financeLettrage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
