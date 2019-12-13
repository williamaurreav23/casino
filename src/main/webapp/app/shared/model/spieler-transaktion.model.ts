import { Transaktion } from 'app/shared/model/enumerations/transaktion.model';

export interface ISpielerTransaktion {
  id?: number;
  wert?: number;
  typ?: Transaktion;
  spielerId?: number;
}

export class SpielerTransaktion implements ISpielerTransaktion {
  constructor(public id?: number, public wert?: number, public typ?: Transaktion, public spielerId?: number) {}
}
