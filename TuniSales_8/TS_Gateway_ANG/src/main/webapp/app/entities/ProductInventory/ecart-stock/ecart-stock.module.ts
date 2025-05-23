import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TsGatewayAngSharedModule } from 'app/shared/shared.module';
import { EcartStockComponent } from './ecart-stock.component';
import { EcartStockDetailComponent } from './ecart-stock-detail.component';
import { EcartStockUpdateComponent } from './ecart-stock-update.component';
import { EcartStockDeleteDialogComponent } from './ecart-stock-delete-dialog.component';
import { ecartStockRoute } from './ecart-stock.route';
import { DxDataGridModule, DxiColumnModule, DxTemplateModule } from 'devextreme-angular';

@NgModule({
  imports: [TsGatewayAngSharedModule, RouterModule.forChild(ecartStockRoute), DxDataGridModule, DxTemplateModule, DxiColumnModule],
  declarations: [EcartStockComponent, EcartStockDetailComponent, EcartStockUpdateComponent, EcartStockDeleteDialogComponent],
  entryComponents: [EcartStockDeleteDialogComponent],
})
export class ProductInventoryEcartStockModule {}
