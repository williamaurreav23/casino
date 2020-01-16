import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { GoogleChartsModule } from 'angular-google-charts';

import './vendor';
import { CasinoSharedModule } from 'app/shared/shared.module';
import { CasinoCoreModule } from 'app/core/core.module';
import { CasinoAppRoutingModule } from './app-routing.module';
import { CasinoHomeModule } from './home/home.module';
import { CasinoEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    CasinoSharedModule,
    CasinoCoreModule,
    CasinoHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    CasinoEntityModule,
    CasinoAppRoutingModule,
    GoogleChartsModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective],
  bootstrap: [MainComponent]
})
export class CasinoAppModule {}
