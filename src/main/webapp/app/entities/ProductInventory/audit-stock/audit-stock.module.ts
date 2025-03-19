import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TsGatewayAngSharedModule } from 'app/shared/shared.module';
import { AuditStockComponent } from './audit-stock.component';
import { AuditStockDetailComponent } from './audit-stock-detail.component';
import { AuditStockUpdateComponent } from './audit-stock-update.component';
import { AuditStockDeleteDialogComponent } from './audit-stock-delete-dialog.component';
import { auditStockRoute } from './audit-stock.route';

@NgModule({
  imports: [TsGatewayAngSharedModule, RouterModule.forChild(auditStockRoute)],
  declarations: [AuditStockComponent, AuditStockDetailComponent, AuditStockUpdateComponent, AuditStockDeleteDialogComponent],
  entryComponents: [AuditStockDeleteDialogComponent],
})
export class ProductInventoryAuditStockModule {}
