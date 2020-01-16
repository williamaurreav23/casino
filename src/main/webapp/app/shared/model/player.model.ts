import { IPlayerMoneyTransaction } from 'app/shared/model/player-money-transaction.model';
import { IPlayerStockTransaction } from 'app/shared/model/player-stock-transaction.model';
import { IPlayerStock } from 'app/shared/model/player-stock.model';

export interface IPlayer {
  id?: number;
  name?: string;
  isChild?: boolean;
  passNumber?: number;
  money?: number;
  playerMoneyTransactions?: IPlayerMoneyTransaction[];
  playerStockTransactions?: IPlayerStockTransaction[];
  playerStocks?: IPlayerStock[];
}

export class Player implements IPlayer {
  constructor(
    public id?: number,
    public name?: string,
    public isChild?: boolean,
    public passNumber?: number,
    public money?: number,
    public playerMoneyTransactions?: IPlayerMoneyTransaction[],
    public playerStockTransactions?: IPlayerStockTransaction[],
    public playerStocks?: IPlayerStock[]
  ) {
    this.isChild = this.isChild || false;
  }
}
