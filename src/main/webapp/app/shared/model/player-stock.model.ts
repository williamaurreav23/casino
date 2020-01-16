export interface IPlayerStock {
  id?: number;
  amount?: number;
  playerId?: number;
  stockId?: number;
}

export class PlayerStock implements IPlayerStock {
  constructor(public id?: number, public amount?: number, public playerId?: number, public stockId?: number) {}
}
