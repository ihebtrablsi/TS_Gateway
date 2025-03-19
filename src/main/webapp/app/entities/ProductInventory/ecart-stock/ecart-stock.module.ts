import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TsGatewayAngSharedModule } from 'app/shared/shared.module';
import { EcartStockComponent } from './ecart-stock.component';
import { EcartStockDetailComponent } from './ecart-stock-detail.component';
import { EcartStockUpdateComponent } from './ecart-stock-update.component';
import { EcartStockDeleteDialogComponent } from './ecart-stock-delete-dialog.component';
import { ecartStockRoute } from './ecart-stock.route';

@NgModule({
  imports: [TsGatewayAngSharedModule, RouterModule.forChild(ecartStockRoute)],
  declarations: [EcartStockComponent, EcartStockDetailComponent, EcartStockUpdateComponent, EcartStockDeleteDialogComponent],
  entryComponents: [EcartStockDeleteDialogComponent],
})
export class ProductInventoryEcartStockModule {}
