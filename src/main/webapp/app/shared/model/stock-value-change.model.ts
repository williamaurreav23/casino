import { Moment } from 'moment';

export interface IStockValueChange {
  id?: number;
  time?: Moment;
  value?: number;
  stockId?: number;
}

export class StockValueChange implements IStockValueChange {
  constructor(public id?: number, public time?: Moment, public value?: number, public stockId?: number) {}
}
