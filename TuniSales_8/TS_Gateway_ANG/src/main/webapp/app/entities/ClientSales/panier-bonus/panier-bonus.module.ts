import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TsGatewayAngSharedModule } from 'app/shared/shared.module';
import { PanierBonusComponent } from './panier-bonus.component';
import { PanierBonusDetailComponent } from './panier-bonus-detail.component';
import { PanierBonusUpdateComponent } from './panier-bonus-update.component';
import { PanierBonusDeleteDialogComponent } from './panier-bonus-delete-dialog.component';
import { panierBonusRoute } from './panier-bonus.route';
import { DxDataGridModule, DxiColumnModule, DxTemplateModule } from 'devextreme-angular';

@NgModule({
  imports: [TsGatewayAngSharedModule, RouterModule.forChild(panierBonusRoute), DxDataGridModule, DxTemplateModule, DxiColumnModule],
  declarations: [PanierBonusComponent, PanierBonusDetailComponent, PanierBonusUpdateComponent, PanierBonusDeleteDialogComponent],
  entryComponents: [PanierBonusDeleteDialogComponent],
})
export class ClientSalesPanierBonusModule {}
