import { Moment } from 'moment';

export interface ISuiviAction {
  id?: number;
  dateSuivi?: Moment;
  indicateurs?: string;
  commentaire?: string;
  actionCommercialeId?: number;
}

export class SuiviAction implements ISuiviAction {
  constructor(
    public id?: number,
    public dateSuivi?: Moment,
    public indicateurs?: string,
    public commentaire?: string,
    public actionCommercialeId?: number
  ) {}
}
