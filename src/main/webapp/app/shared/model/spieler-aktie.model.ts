export interface ISpielerAktie {
  id?: number;
  anzahl?: number;
  spielerId?: number;
  aktieId?: number;
}

export class SpielerAktie implements ISpielerAktie {
  constructor(public id?: number, public anzahl?: number, public spielerId?: number, public aktieId?: number) {}
}
