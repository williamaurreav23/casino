import { IPlayerStockTransaction } from 'app/shared/model/player-stock-transaction.model';
import { IPlayerStock } from 'app/shared/model/player-stock.model';
import { IStockValueChange } from 'app/shared/model/stock-value-change.model';

export interface IStock {
  id?: number;
  name?: string;
  color?: string;
  value?: number;
  playerStockTransactions?: IPlayerStockTransaction[];
  playerStocks?: IPlayerStock[];
  stockValueChanges?: IStockValueChange[];
}

export class Stock implements IStock {
  constructor(
    public id?: number,
    public name?: string,
    public color?: string,
    public value?: number,
    public playerStockTransactions?: IPlayerStockTransaction[],
    public playerStocks?: IPlayerStock[],
    public stockValueChanges?: IStockValueChange[]
  ) {}
}
