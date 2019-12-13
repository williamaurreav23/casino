export interface IAktieWert {
  id?: number;
  wert?: number;
  aktieId?: number;
}

export class AktieWert implements IAktieWert {
  constructor(public id?: number, public wert?: number, public aktieId?: number) {}
}
