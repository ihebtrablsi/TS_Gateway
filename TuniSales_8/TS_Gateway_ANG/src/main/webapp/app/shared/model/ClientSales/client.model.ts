export interface IClient {
  id?: number;
  nom?: string;
  email?: string;
  telephone?: string;
  adresse?: string;
  typeClient?: string;
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public nom?: string,
    public email?: string,
    public telephone?: string,
    public adresse?: string,
    public typeClient?: string
  ) {}
}
