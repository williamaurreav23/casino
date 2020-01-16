import { Moment } from 'moment';

export interface IPlayerStockTransaction {
  id?: number;
  time?: Moment;
  amount?: number;
  playerId?: number;
  stockId?: number;
}

export class PlayerStockTransaction implements IPlayerStockTransaction {
  constructor(public id?: number, public time?: Moment, public amount?: number, public playerId?: number, public stockId?: number) {}
}
