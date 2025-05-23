export interface ILigneCommande {
  id?: number;
  quantite?: number;
  prixUnitaire?: number;
  commandeId?: number;
}

export class LigneCommande implements ILigneCommande {
  constructor(public id?: number, public quantite?: number, public prixUnitaire?: number, public commandeId?: number) {}
}
