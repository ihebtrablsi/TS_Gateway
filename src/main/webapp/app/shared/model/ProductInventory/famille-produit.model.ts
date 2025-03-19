export interface IFamilleProduit {
  id?: number;
  nom?: string;
  description?: string;
  options?: string;
}

export class FamilleProduit implements IFamilleProduit {
  constructor(public id?: number, public nom?: string, public description?: string, public options?: string) {}
}
