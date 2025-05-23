import { Moment } from 'moment';

export interface IBonus {
  id?: number;
  montant?: number;
  dateAttribution?: Moment;
  statut?: string;
  incentiveId?: number;
}

export class Bonus implements IBonus {
  constructor(
    public id?: number,
    public montant?: number,
    public dateAttribution?: Moment,
    public statut?: string,
    public incentiveId?: number
  ) {}
}
