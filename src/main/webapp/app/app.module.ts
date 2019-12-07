import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { CasinoSharedModule } from 'app/shared/shared.module';
import { CasinoCoreModule } from 'app/core/core.module';
import { CasinoAppRoutingModule } from './app-routing.module';
import { CasinoHomeModule } from './home/home.module';
import { CasinoEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
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
    CasinoAppRoutingModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [JhiMainComponent]
})
export class CasinoAppModule {}
