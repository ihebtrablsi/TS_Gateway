import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TsGatewayAngSharedModule } from 'app/shared/shared.module';
import { FamilleProduitComponent } from './famille-produit.component';
import { FamilleProduitDetailComponent } from './famille-produit-detail.component';
import { FamilleProduitUpdateComponent } from './famille-produit-update.component';
import { FamilleProduitDeleteDialogComponent } from './famille-produit-delete-dialog.component';
import { familleProduitRoute } from './famille-produit.route';

@NgModule({
  imports: [TsGatewayAngSharedModule, RouterModule.forChild(familleProduitRoute)],
  declarations: [
    FamilleProduitComponent,
    FamilleProduitDetailComponent,
    FamilleProduitUpdateComponent,
    FamilleProduitDeleteDialogComponent,
  ],
  entryComponents: [FamilleProduitDeleteDialogComponent],
})
export class ProductInventoryFamilleProduitModule {}
