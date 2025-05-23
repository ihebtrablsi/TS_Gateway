import { Moment } from 'moment';

export interface IPromotion {
  id?: number;
  nom?: string;
  dateDebut?: Moment;
  dateFin?: Moment;
  remise?: number;
  produitId?: number;
}

export class Promotion implements IPromotion {
  constructor(
    public id?: number,
    public nom?: string,
    public dateDebut?: Moment,
    public dateFin?: Moment,
    public remise?: number,
    public produitId?: number
  ) {}
}
