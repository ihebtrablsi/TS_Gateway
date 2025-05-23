import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TsGatewayAngSharedModule } from 'app/shared/shared.module';
import { LettrageComponent } from './lettrage.component';
import { LettrageDetailComponent } from './lettrage-detail.component';
import { LettrageUpdateComponent } from './lettrage-update.component';
import { LettrageDeleteDialogComponent } from './lettrage-delete-dialog.component';
import { lettrageRoute } from './lettrage.route';
import { DxDataGridModule, DxiColumnModule, DxTemplateModule } from 'devextreme-angular';

@NgModule({
  imports: [TsGatewayAngSharedModule, RouterModule.forChild(lettrageRoute), DxDataGridModule, DxTemplateModule, DxiColumnModule],
  declarations: [LettrageComponent, LettrageDetailComponent, LettrageUpdateComponent, LettrageDeleteDialogComponent],
  entryComponents: [LettrageDeleteDialogComponent],
})
export class FinanceLettrageModule {}
