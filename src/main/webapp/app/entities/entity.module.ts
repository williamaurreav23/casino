import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'spieler',
        loadChildren: () => import('./spieler/spieler.module').then(m => m.CasinoSpielerModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class CasinoEntityModule {}
