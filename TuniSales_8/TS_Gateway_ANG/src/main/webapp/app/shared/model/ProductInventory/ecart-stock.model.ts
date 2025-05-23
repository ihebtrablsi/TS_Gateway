export interface IEcartStock {
  id?: number;
  quantiteTheorique?: number;
  quantitePhysique?: number;
  ecart?: number;
  commentaire?: string;
  produitId?: number;
  auditId?: number;
}

export class EcartStock implements IEcartStock {
  constructor(
    public id?: number,
    public quantiteTheorique?: number,
    public quantitePhysique?: number,
    public ecart?: number,
    public commentaire?: string,
    public produitId?: number,
    public auditId?: number
  ) {}
}
