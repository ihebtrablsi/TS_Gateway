import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TsGatewayAngSharedModule } from 'app/shared/shared.module';
import { FamilleProduitComponent } from './famille-produit.component';
import { FamilleProduitDetailComponent } from './famille-produit-detail.component';
import { FamilleProduitUpdateComponent } from './famille-produit-update.component';
import { FamilleProduitDeleteDialogComponent } from './famille-produit-delete-dialog.component';
import { familleProduitRoute } from './famille-produit.route';
import { DxDataGridModule, DxiColumnModule, DxTemplateModule } from 'devextreme-angular';
import {NgJsonEditorModule} from "ang-jsoneditor";

@NgModule({
    imports: [TsGatewayAngSharedModule, RouterModule.forChild(familleProduitRoute), DxDataGridModule, DxTemplateModule, DxiColumnModule, NgJsonEditorModule],
  declarations: [
    FamilleProduitComponent,
    FamilleProduitDetailComponent,
    FamilleProduitUpdateComponent,
    FamilleProduitDeleteDialogComponent,
  ],
  entryComponents: [FamilleProduitDeleteDialogComponent],
})
export class ProductInventoryFamilleProduitModule {}
