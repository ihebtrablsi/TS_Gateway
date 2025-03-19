export interface IPanierBonus {
  id?: number;
  totalPoints?: number;
  totalValeur?: number;
}

export class PanierBonus implements IPanierBonus {
  constructor(public id?: number, public totalPoints?: number, public totalValeur?: number) {}
}
