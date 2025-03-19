import { Moment } from 'moment';

export interface IRistourne {
  id?: number;
  montant?: number;
  dateAttribution?: Moment;
}

export class Ristourne implements IRistourne {
  constructor(public id?: number, public montant?: number, public dateAttribution?: Moment) {}
}
