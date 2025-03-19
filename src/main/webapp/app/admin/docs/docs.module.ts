import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { TsGatewayAngSharedModule } from 'app/shared/shared.module';

import { DocsComponent } from './docs.component';

import { docsRoute } from './docs.route';

@NgModule({
  imports: [TsGatewayAngSharedModule, RouterModule.forChild([docsRoute])],
  declarations: [DocsComponent],
})
export class DocsModule {}
