import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TsGatewayAngSharedModule } from 'app/shared/shared.module';
import { LigneCommandeComponent } from './ligne-commande.component';
import { LigneCommandeDetailComponent } from './ligne-commande-detail.component';
import { LigneCommandeUpdateComponent } from './ligne-commande-update.component';
import { LigneCommandeDeleteDialogComponent } from './ligne-commande-delete-dialog.component';
import { ligneCommandeRoute } from './ligne-commande.route';
import { DxDataGridModule, DxiColumnModule, DxTemplateModule } from 'devextreme-angular';

@NgModule({
  imports: [TsGatewayAngSharedModule, RouterModule.forChild(ligneCommandeRoute), DxDataGridModule, DxTemplateModule, DxiColumnModule],
  declarations: [LigneCommandeComponent, LigneCommandeDetailComponent, LigneCommandeUpdateComponent, LigneCommandeDeleteDialogComponent],
  entryComponents: [LigneCommandeDeleteDialogComponent],
})
export class ClientSalesLigneCommandeModule {}
