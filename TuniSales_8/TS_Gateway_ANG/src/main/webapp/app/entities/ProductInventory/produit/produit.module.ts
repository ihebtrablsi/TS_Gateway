import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TsGatewayAngSharedModule } from 'app/shared/shared.module';
import { ProduitComponent } from './produit.component';
import { ProduitDetailComponent } from './produit-detail.component';
import { ProduitUpdateComponent } from './produit-update.component';
import { ProduitDeleteDialogComponent } from './produit-delete-dialog.component';
import { produitRoute } from './produit.route';
import { DxDataGridModule } from 'devextreme-angular';
import {NgJsonEditorModule} from "ang-jsoneditor";

@NgModule({
    imports: [TsGatewayAngSharedModule, RouterModule.forChild(produitRoute), DxDataGridModule, NgJsonEditorModule],
  declarations: [ProduitComponent, ProduitDetailComponent, ProduitUpdateComponent, ProduitDeleteDialogComponent],
  entryComponents: [ProduitDeleteDialogComponent],
})
export class ProductInventoryProduitModule {}
