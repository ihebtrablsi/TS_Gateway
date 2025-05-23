export interface IFamilleProduit {
  id?: number;
  nom?: string;
  description?: string;
  options?: any;
}

export class FamilleProduit implements IFamilleProduit {
  constructor(public id?: number, public nom?: string, public description?: string, public options?: any) {}
}
