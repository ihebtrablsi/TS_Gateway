import { Moment } from 'moment';

export interface IIncentive {
  id?: number;
  nom?: string;
  description?: string;
  dateDebut?: Moment;
  dateFin?: Moment;
  regles?: string;
  bonus?: string;
}

export class Incentive implements IIncentive {
  constructor(
    public id?: number,
    public nom?: string,
    public description?: string,
    public dateDebut?: Moment,
    public dateFin?: Moment,
    public regles?: string,
    public bonus?: string
  ) {}
}
