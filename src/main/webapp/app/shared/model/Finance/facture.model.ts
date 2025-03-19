import { Moment } from 'moment';

export interface IFacture {
  id?: number;
  numeroFacture?: string;
  dateFacture?: Moment;
  montantTotal?: number;
  statut?: string;
  dateEcheance?: Moment;
}

export class Facture implements IFacture {
  constructor(
    public id?: number,
    public numeroFacture?: string,
    public dateFacture?: Moment,
    public montantTotal?: number,
    public statut?: string,
    public dateEcheance?: Moment
  ) {}
}
