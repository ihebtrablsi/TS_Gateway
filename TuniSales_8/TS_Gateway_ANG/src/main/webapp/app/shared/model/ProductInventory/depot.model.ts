export interface IDepot {
  id?: number;
  nom?: string;
  adresse?: string;
  typeDepot?: string;
  capacite?: number;
}

export class Depot implements IDepot {
  constructor(public id?: number, public nom?: string, public adresse?: string, public typeDepot?: string, public capacite?: number) {}
}
