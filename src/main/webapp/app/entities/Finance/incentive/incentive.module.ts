import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TsGatewayAngSharedModule } from 'app/shared/shared.module';
import { IncentiveComponent } from './incentive.component';
import { IncentiveDetailComponent } from './incentive-detail.component';
import { IncentiveUpdateComponent } from './incentive-update.component';
import { IncentiveDeleteDialogComponent } from './incentive-delete-dialog.component';
import { incentiveRoute } from './incentive.route';
import { DxDataGridModule, DxiColumnModule, DxTemplateModule } from 'devextreme-angular';

@NgModule({
  imports: [TsGatewayAngSharedModule, RouterModule.forChild(incentiveRoute), DxDataGridModule, DxTemplateModule, DxiColumnModule],
  declarations: [IncentiveComponent, IncentiveDetailComponent, IncentiveUpdateComponent, IncentiveDeleteDialogComponent],
  entryComponents: [IncentiveDeleteDialogComponent],
})
export class FinanceIncentiveModule {}
