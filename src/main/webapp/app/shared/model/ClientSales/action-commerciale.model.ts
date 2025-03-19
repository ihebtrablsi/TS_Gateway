import { Moment } from 'moment';

export interface IActionCommerciale {
  id?: number;
  nom?: string;
  description?: string;
  dateDebut?: Moment;
  dateFin?: Moment;
  typeAction?: string;
  statut?: string;
  pointDeVenteId?: number;
}

export class ActionCommerciale implements IActionCommerciale {
  constructor(
    public id?: number,
    public nom?: string,
    public description?: string,
    public dateDebut?: Moment,
    public dateFin?: Moment,
    public typeAction?: string,
    public statut?: string,
    public pointDeVenteId?: number
  ) {}
}
