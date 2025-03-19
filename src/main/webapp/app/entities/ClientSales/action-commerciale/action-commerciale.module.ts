import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TsGatewayAngSharedModule } from 'app/shared/shared.module';
import { ActionCommercialeComponent } from './action-commerciale.component';
import { ActionCommercialeDetailComponent } from './action-commerciale-detail.component';
import { ActionCommercialeUpdateComponent } from './action-commerciale-update.component';
import { ActionCommercialeDeleteDialogComponent } from './action-commerciale-delete-dialog.component';
import { actionCommercialeRoute } from './action-commerciale.route';

@NgModule({
  imports: [TsGatewayAngSharedModule, RouterModule.forChild(actionCommercialeRoute)],
  declarations: [
    ActionCommercialeComponent,
    ActionCommercialeDetailComponent,
    ActionCommercialeUpdateComponent,
    ActionCommercialeDeleteDialogComponent,
  ],
  entryComponents: [ActionCommercialeDeleteDialogComponent],
})
export class ClientSalesActionCommercialeModule {}
