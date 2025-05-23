export interface ICode {
  id?: number;
  code?: string;
  produitId?: number;
}

export class Code implements ICode {
  constructor(public id?: number, public code?: string, public produitId?: number) {}
}
