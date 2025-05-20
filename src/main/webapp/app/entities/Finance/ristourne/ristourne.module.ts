import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TsGatewayAngSharedModule } from 'app/shared/shared.module';
import { RistourneComponent } from './ristourne.component';
import { RistourneDetailComponent } from './ristourne-detail.component';
import { RistourneUpdateComponent } from './ristourne-update.component';
import { RistourneDeleteDialogComponent } from './ristourne-delete-dialog.component';
import { ristourneRoute } from './ristourne.route';
import { DxDataGridModule, DxiColumnModule, DxTemplateModule } from 'devextreme-angular';

@NgModule({
  imports: [TsGatewayAngSharedModule, RouterModule.forChild(ristourneRoute), DxDataGridModule, DxTemplateModule, DxiColumnModule],
  declarations: [RistourneComponent, RistourneDetailComponent, RistourneUpdateComponent, RistourneDeleteDialogComponent],
  entryComponents: [RistourneDeleteDialogComponent],
})
export class FinanceRistourneModule {}
