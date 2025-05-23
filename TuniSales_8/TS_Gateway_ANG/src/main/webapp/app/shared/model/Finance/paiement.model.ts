import { Moment } from 'moment';

export interface IPaiement {
  id?: number;
  reference?: string;
  datePaiement?: Moment;
  montant?: number;
  modePaiement?: string;
  statut?: string;
  factureId?: number;
  compteId?: number;
}

export class Paiement implements IPaiement {
  constructor(
    public id?: number,
    public reference?: string,
    public datePaiement?: Moment,
    public montant?: number,
    public modePaiement?: string,
    public statut?: string,
    public factureId?: number,
    public compteId?: number
  ) {}
}
