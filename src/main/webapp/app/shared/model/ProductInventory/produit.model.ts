import { ICode } from 'app/shared/model/ProductInventory/code.model';

export interface IProduit {
  id?: number;
  nom?: string;
  description?: string;
  prix?: number;
  stock?: number;
  categorie?: string;
  imageUrl?: string;
  details?: string;
  codes?: ICode[];
  familleId?: number;
}

export class Produit implements IProduit {
  constructor(
    public id?: number,
    public nom?: string,
    public description?: string,
    public prix?: number,
    public stock?: number,
    public categorie?: string,
    public imageUrl?: string,
    public details?: string,
    public codes?: ICode[],
    public familleId?: number
  ) {}
}
