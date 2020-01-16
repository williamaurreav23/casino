import { IUser } from 'app/core/user/user.model';
import { Transaction } from 'app/shared/model/enumerations/transaction.model';

export interface IUserExtra {
  id?: number;
  transaction?: Transaction;
  user?: IUser;
}

export class UserExtra implements IUserExtra {
  constructor(public id?: number, public transaction?: Transaction, public user?: IUser) {}
}
