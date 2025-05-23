import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TsGatewayAngSharedModule } from 'app/shared/shared.module';
import { CommandeComponent } from './commande.component';
import { CommandeDetailComponent } from './commande-detail.component';
import { CommandeUpdateComponent } from './commande-update.component';
import { CommandeDeleteDialogComponent } from './commande-delete-dialog.component';
import { commandeRoute } from './commande.route';
import { DxDataGridModule, DxiColumnModule, DxTemplateModule } from 'devextreme-angular';
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    TsGatewayAngSharedModule,
    RouterModule.forChild(commandeRoute),
    DxDataGridModule,
    DxTemplateModule,
    DxiColumnModule,
    FormsModule,
  ],
  declarations: [CommandeComponent, CommandeDetailComponent, CommandeUpdateComponent, CommandeDeleteDialogComponent],
  entryComponents: [CommandeDeleteDialogComponent],
})
export class ClientSalesCommandeModule {}
