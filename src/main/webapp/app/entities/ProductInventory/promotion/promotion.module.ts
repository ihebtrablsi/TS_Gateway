import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TsGatewayAngSharedModule } from 'app/shared/shared.module';
import { PromotionComponent } from './promotion.component';
import { PromotionDetailComponent } from './promotion-detail.component';
import { PromotionUpdateComponent } from './promotion-update.component';
import { PromotionDeleteDialogComponent } from './promotion-delete-dialog.component';
import { promotionRoute } from './promotion.route';
import { DxDataGridModule, DxiColumnModule, DxTemplateModule } from 'devextreme-angular';

@NgModule({
  imports: [TsGatewayAngSharedModule, RouterModule.forChild(promotionRoute), DxDataGridModule, DxTemplateModule, DxiColumnModule],
  declarations: [PromotionComponent, PromotionDetailComponent, PromotionUpdateComponent, PromotionDeleteDialogComponent],
  entryComponents: [PromotionDeleteDialogComponent],
})
export class ProductInventoryPromotionModule {}
