import { Moment } from 'moment';
import { Transaction } from 'app/shared/model/enumerations/transaction.model';

export interface IPlayerMoneyTransaction {
  id?: number;
  time?: Moment;
  transaction?: Transaction;
  value?: number;
  playerId?: number;
}

export class PlayerMoneyTransaction implements IPlayerMoneyTransaction {
  constructor(
    public id?: number,
    public time?: Moment,
    public transaction?: Transaction,
    public value?: number,
    public playerId?: number
  ) {}
}
