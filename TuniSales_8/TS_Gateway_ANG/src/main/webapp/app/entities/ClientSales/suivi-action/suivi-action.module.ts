import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TsGatewayAngSharedModule } from 'app/shared/shared.module';
import { SuiviActionComponent } from './suivi-action.component';
import { SuiviActionDetailComponent } from './suivi-action-detail.component';
import { SuiviActionUpdateComponent } from './suivi-action-update.component';
import { SuiviActionDeleteDialogComponent } from './suivi-action-delete-dialog.component';
import { suiviActionRoute } from './suivi-action.route';
import { DxDataGridModule, DxiColumnModule, DxTemplateModule } from 'devextreme-angular';

@NgModule({
  imports: [TsGatewayAngSharedModule, RouterModule.forChild(suiviActionRoute), DxDataGridModule, DxTemplateModule, DxiColumnModule],
  declarations: [SuiviActionComponent, SuiviActionDetailComponent, SuiviActionUpdateComponent, SuiviActionDeleteDialogComponent],
  entryComponents: [SuiviActionDeleteDialogComponent],
})
export class ClientSalesSuiviActionModule {}
