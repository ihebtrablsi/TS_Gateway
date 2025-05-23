import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TsGatewayAngSharedModule } from 'app/shared/shared.module';
import { MouvementStockComponent } from './mouvement-stock.component';
import { MouvementStockDetailComponent } from './mouvement-stock-detail.component';
import { MouvementStockUpdateComponent } from './mouvement-stock-update.component';
import { MouvementStockDeleteDialogComponent } from './mouvement-stock-delete-dialog.component';
import { mouvementStockRoute } from './mouvement-stock.route';
import { DxDataGridModule, DxiColumnModule, DxTemplateModule } from 'devextreme-angular';

@NgModule({
  imports: [TsGatewayAngSharedModule, RouterModule.forChild(mouvementStockRoute), DxDataGridModule, DxTemplateModule, DxiColumnModule],
  declarations: [
    MouvementStockComponent,
    MouvementStockDetailComponent,
    MouvementStockUpdateComponent,
    MouvementStockDeleteDialogComponent,
  ],
  entryComponents: [MouvementStockDeleteDialogComponent],
})
export class ProductInventoryMouvementStockModule {}
