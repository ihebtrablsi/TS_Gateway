import { Moment } from 'moment';
import { IEcartStock } from 'app/shared/model/ProductInventory/ecart-stock.model';

export interface IAuditStock {
  id?: number;
  dateAudit?: Moment;
  statut?: string;
  commentaire?: string;
  ecarts?: IEcartStock[];
  depotId?: number;
}

export class AuditStock implements IAuditStock {
  constructor(
    public id?: number,
    public dateAudit?: Moment,
    public statut?: string,
    public commentaire?: string,
    public ecarts?: IEcartStock[],
    public depotId?: number
  ) {}
}
