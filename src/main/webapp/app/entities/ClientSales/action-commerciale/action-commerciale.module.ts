import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TsGatewayAngSharedModule } from 'app/shared/shared.module';
import { ActionCommercialeComponent } from './action-commerciale.component';
import { ActionCommercialeDetailComponent } from './action-commerciale-detail.component';
import { ActionCommercialeUpdateComponent } from './action-commerciale-update.component';
import { ActionCommercialeDeleteDialogComponent } from './action-commerciale-delete-dialog.component';
import { actionCommercialeRoute } from './action-commerciale.route';
import { DxDataGridModule, DxiColumnModule, DxTemplateModule } from 'devextreme-angular';

@NgModule({
  imports: [TsGatewayAngSharedModule, RouterModule.forChild(actionCommercialeRoute), DxDataGridModule, DxTemplateModule, DxiColumnModule],
  declarations: [
    ActionCommercialeComponent,
    ActionCommercialeDetailComponent,
    ActionCommercialeUpdateComponent,
    ActionCommercialeDeleteDialogComponent,
  ],
  entryComponents: [ActionCommercialeDeleteDialogComponent],
})
export class ClientSalesActionCommercialeModule {}
