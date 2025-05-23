export interface IPointDeVente {
  id?: number;
  nom?: string;
  adresse?: string;
  ville?: string;
  codePostal?: string;
  telephone?: string;
  latitude?: number;
  longitude?: number;
}

export class PointDeVente implements IPointDeVente {
  constructor(
    public id?: number,
    public nom?: string,
    public adresse?: string,
    public ville?: string,
    public codePostal?: string,
    public telephone?: string,
    public latitude?: number,
    public longitude?: number
  ) {}
}
