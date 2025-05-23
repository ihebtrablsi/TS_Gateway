import { Moment } from 'moment';

export interface IMouvementStock {
  id?: number;
  typeMouvement?: string;
  quantite?: number;
  dateMouvement?: Moment;
  produitId?: number;
  depotId?: number;
}

export class MouvementStock implements IMouvementStock {
  constructor(
    public id?: number,
    public typeMouvement?: string,
    public quantite?: number,
    public dateMouvement?: Moment,
    public produitId?: number,
    public depotId?: number
  ) {}
}
