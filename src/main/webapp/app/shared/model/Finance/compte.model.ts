export interface ICompte {
  id?: number;
  numeroCompte?: string;
  solde?: number;
  typeCompte?: string;
}

export class Compte implements ICompte {
  constructor(public id?: number, public numeroCompte?: string, public solde?: number, public typeCompte?: string) {}
}
