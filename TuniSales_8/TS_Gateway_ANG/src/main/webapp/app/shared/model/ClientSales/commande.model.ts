import { Moment } from 'moment';

export interface ICommande {
  id?: number;
  dateCommande?: Moment;
  statut?: string;
  total?: number;
  clientId?: number;
  pointDeVenteId?: number;
}

export class Commande implements ICommande {
  constructor(
    public id?: number,
    public dateCommande?: Moment,
    public statut?: string,
    public total?: number,
    public clientId?: number,
    public pointDeVenteId?: number
  ) {}
}
