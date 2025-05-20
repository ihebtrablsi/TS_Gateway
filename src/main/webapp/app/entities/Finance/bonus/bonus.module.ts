import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TsGatewayAngSharedModule } from 'app/shared/shared.module';
import { BonusComponent } from './bonus.component';
import { BonusDetailComponent } from './bonus-detail.component';
import { BonusUpdateComponent } from './bonus-update.component';
import { BonusDeleteDialogComponent } from './bonus-delete-dialog.component';
import { bonusRoute } from './bonus.route';
import { DxDataGridModule, DxiColumnModule, DxTemplateModule } from 'devextreme-angular';

@NgModule({
  imports: [TsGatewayAngSharedModule, RouterModule.forChild(bonusRoute), DxDataGridModule, DxTemplateModule, DxiColumnModule],
  declarations: [BonusComponent, BonusDetailComponent, BonusUpdateComponent, BonusDeleteDialogComponent],
  entryComponents: [BonusDeleteDialogComponent],
})
export class FinanceBonusModule {}
