import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TsGatewayAngSharedModule } from 'app/shared/shared.module';
import { CodeComponent } from './code.component';
import { CodeDetailComponent } from './code-detail.component';
import { CodeUpdateComponent } from './code-update.component';
import { CodeDeleteDialogComponent } from './code-delete-dialog.component';
import { codeRoute } from './code.route';
import { DxDataGridModule, DxiColumnModule, DxTemplateModule } from 'devextreme-angular';

@NgModule({
  imports: [TsGatewayAngSharedModule, RouterModule.forChild(codeRoute), DxDataGridModule, DxTemplateModule, DxiColumnModule],
  declarations: [CodeComponent, CodeDetailComponent, CodeUpdateComponent, CodeDeleteDialogComponent],
  entryComponents: [CodeDeleteDialogComponent],
})
export class ProductInventoryCodeModule {}
