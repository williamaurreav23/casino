export interface IGameEnded {
  id?: number;
}

export class GameEnded implements IGameEnded {
  constructor(public id?: number) {}
}
