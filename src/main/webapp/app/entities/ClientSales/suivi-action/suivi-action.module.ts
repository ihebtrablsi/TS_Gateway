import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TsGatewayAngSharedModule } from 'app/shared/shared.module';
import { SuiviActionComponent } from './suivi-action.component';
import { SuiviActionDetailComponent } from './suivi-action-detail.component';
import { SuiviActionUpdateComponent } from './suivi-action-update.component';
import { SuiviActionDeleteDialogComponent } from './suivi-action-delete-dialog.component';
import { suiviActionRoute } from './suivi-action.route';

@NgModule({
  imports: [TsGatewayAngSharedModule, RouterModule.forChild(suiviActionRoute)],
  declarations: [SuiviActionComponent, SuiviActionDetailComponent, SuiviActionUpdateComponent, SuiviActionDeleteDialogComponent],
  entryComponents: [SuiviActionDeleteDialogComponent],
})
export class ClientSalesSuiviActionModule {}
