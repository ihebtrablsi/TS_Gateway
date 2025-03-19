import { Moment } from 'moment';

export interface ILettrage {
  id?: number;
  dateLettrage?: Moment;
  montantLettre?: number;
  factureId?: number;
  paiementId?: number;
}

export class Lettrage implements ILettrage {
  constructor(
    public id?: number,
    public dateLettrage?: Moment,
    public montantLettre?: number,
    public factureId?: number,
    public paiementId?: number
  ) {}
}
