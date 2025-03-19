import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TsGatewayAngSharedModule } from 'app/shared/shared.module';
import { PaiementComponent } from './paiement.component';
import { PaiementDetailComponent } from './paiement-detail.component';
import { PaiementUpdateComponent } from './paiement-update.component';
import { PaiementDeleteDialogComponent } from './paiement-delete-dialog.component';
import { paiementRoute } from './paiement.route';

@NgModule({
  imports: [TsGatewayAngSharedModule, RouterModule.forChild(paiementRoute)],
  declarations: [PaiementComponent, PaiementDetailComponent, PaiementUpdateComponent, PaiementDeleteDialogComponent],
  entryComponents: [PaiementDeleteDialogComponent],
})
export class FinancePaiementModule {}
