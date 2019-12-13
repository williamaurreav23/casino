import { Moment } from 'moment';
import { ISpieler } from 'app/shared/model/spieler.model';
import { IAktie } from 'app/shared/model/aktie.model';

export interface ISpielerAktieHistory {
  id?: number;
  anzahl?: number;
  creationTime?: Moment;
  spieler?: ISpieler;
  aktie?: IAktie;
}

export class SpielerAktieHistory implements ISpielerAktieHistory {
  constructor(public id?: number, public anzahl?: number, public creationTime?: Moment, public spieler?: ISpieler, public aktie?: IAktie) {}
}
