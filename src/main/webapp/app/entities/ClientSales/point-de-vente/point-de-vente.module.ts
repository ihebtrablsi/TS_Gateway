import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TsGatewayAngSharedModule } from 'app/shared/shared.module';
import { PointDeVenteComponent } from './point-de-vente.component';
import { PointDeVenteDetailComponent } from './point-de-vente-detail.component';
import { PointDeVenteUpdateComponent } from './point-de-vente-update.component';
import { PointDeVenteDeleteDialogComponent } from './point-de-vente-delete-dialog.component';
import { pointDeVenteRoute } from './point-de-vente.route';

@NgModule({
  imports: [TsGatewayAngSharedModule, RouterModule.forChild(pointDeVenteRoute)],
  declarations: [PointDeVenteComponent, PointDeVenteDetailComponent, PointDeVenteUpdateComponent, PointDeVenteDeleteDialogComponent],
  entryComponents: [PointDeVenteDeleteDialogComponent],
})
export class ClientSalesPointDeVenteModule {}
