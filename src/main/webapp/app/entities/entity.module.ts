import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'spieler',
        loadChildren: () => import('./spieler/spieler.module').then(m => m.CasinoSpielerModule)
      },
      {
        path: 'aktie',
        loadChildren: () => import('./aktie/aktie.module').then(m => m.CasinoAktieModule)
      },
      {
        path: 'spieler-transaktion',
        loadChildren: () => import('./spieler-transaktion/spieler-transaktion.module').then(m => m.CasinoSpielerTransaktionModule)
      },
      {
        path: 'aktie-wert',
        loadChildren: () => import('./aktie-wert/aktie-wert.module').then(m => m.CasinoAktieWertModule)
      },
      {
        path: 'spieler-aktie',
        loadChildren: () => import('./spieler-aktie/spieler-aktie.module').then(m => m.CasinoSpielerAktieModule)
      },
      {
        path: 'spieler-aktie-history',
        loadChildren: () => import('./spieler-aktie-history/spieler-aktie-history.module').then(m => m.CasinoSpielerAktieHistoryModule)
      },
      {
        path: 'aktie-wert-history',
        loadChildren: () => import('./aktie-wert-history/aktie-wert-history.module').then(m => m.CasinoAktieWertHistoryModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class CasinoEntityModule {}
