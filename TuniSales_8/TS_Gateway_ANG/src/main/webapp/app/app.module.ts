import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { TsGatewayAngSharedModule } from 'app/shared/shared.module';
import { TsGatewayAngCoreModule } from 'app/core/core.module';
import { TsGatewayAngAppRoutingModule } from './app-routing.module';
import { TsGatewayAngHomeModule } from './home/home.module';
import { TsGatewayAngEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { RouterModule } from '@angular/router';
import { SidenavComponent } from 'app/layouts/sidenav/sidenav.component';
import { DxDataGridModule, DxTemplateModule } from 'devextreme-angular';
import { ReplaceHyphensPipe } from 'app/shared/pipes/ReplaceHyphensPipe';
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    BrowserModule,
    TsGatewayAngSharedModule,
    TsGatewayAngCoreModule,
    TsGatewayAngHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    TsGatewayAngEntityModule,
    TsGatewayAngAppRoutingModule,
    FormsModule,
    RouterModule,
    DxDataGridModule,
    DxTemplateModule,
  ],
  declarations: [
    MainComponent,
    NavbarComponent,
    ErrorComponent,
    PageRibbonComponent,
    ActiveMenuDirective,
    FooterComponent,
    SidenavComponent,
    ReplaceHyphensPipe,
  ],
  bootstrap: [MainComponent],
  exports: [ReplaceHyphensPipe],
})
export class TsGatewayAngAppModule {}
