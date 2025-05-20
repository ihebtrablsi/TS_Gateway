import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TsGatewayAngSharedModule } from 'app/shared/shared.module';
import { CompteComponent } from './compte.component';
import { CompteDetailComponent } from './compte-detail.component';
import { CompteUpdateComponent } from './compte-update.component';
import { CompteDeleteDialogComponent } from './compte-delete-dialog.component';
import { compteRoute } from './compte.route';
import { DxDataGridModule, DxiColumnModule, DxTemplateModule } from 'devextreme-angular';

@NgModule({
  imports: [TsGatewayAngSharedModule, RouterModule.forChild(compteRoute), DxDataGridModule, DxTemplateModule, DxiColumnModule],
  declarations: [CompteComponent, CompteDetailComponent, CompteUpdateComponent, CompteDeleteDialogComponent],
  entryComponents: [CompteDeleteDialogComponent],
})
export class FinanceCompteModule {}
