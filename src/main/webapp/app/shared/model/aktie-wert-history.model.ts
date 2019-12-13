import { Moment } from 'moment';
import { IAktie } from 'app/shared/model/aktie.model';

export interface IAktieWertHistory {
  id?: number;
  wert?: number;
  creationTime?: Moment;
  aktie?: IAktie;
}

export class AktieWertHistory implements IAktieWertHistory {
  constructor(public id?: number, public wert?: number, public creationTime?: Moment, public aktie?: IAktie) {}
}
