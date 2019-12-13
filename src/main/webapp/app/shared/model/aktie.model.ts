import { ISpielerAktie } from 'app/shared/model/spieler-aktie.model';
import { IAktieWert } from 'app/shared/model/aktie-wert.model';

export interface IAktie {
  id?: number;
  name?: string;
  spielerAkties?: ISpielerAktie[];
  aktieWerts?: IAktieWert[];
}

export class Aktie implements IAktie {
  constructor(public id?: number, public name?: string, public spielerAkties?: ISpielerAktie[], public aktieWerts?: IAktieWert[]) {}
}
