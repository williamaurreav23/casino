import { ISpielerAktie } from 'app/shared/model/spieler-aktie.model';
import { ISpielerTransaktion } from 'app/shared/model/spieler-transaktion.model';

export interface ISpieler {
  id?: number;
  vorname?: string;
  nachname?: string;
  isKind?: boolean;
  kennzahl?: number;
  spielerAkties?: ISpielerAktie[];
  spielerTransaktions?: ISpielerTransaktion[];
}

export class Spieler implements ISpieler {
  constructor(
    public id?: number,
    public vorname?: string,
    public nachname?: string,
    public isKind?: boolean,
    public kennzahl?: number,
    public spielerAkties?: ISpielerAktie[],
    public spielerTransaktions?: ISpielerTransaktion[]
  ) {
    this.isKind = this.isKind || false;
  }
}
