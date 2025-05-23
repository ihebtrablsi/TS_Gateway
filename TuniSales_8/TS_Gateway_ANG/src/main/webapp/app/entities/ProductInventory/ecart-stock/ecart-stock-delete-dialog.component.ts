import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEcartStock } from 'app/shared/model/ProductInventory/ecart-stock.model';
import { EcartStockService } from './ecart-stock.service';

@Component({
  templateUrl: './ecart-stock-delete-dialog.component.html',
})
export class EcartStockDeleteDialogComponent {
  ecartStock?: IEcartStock;

  constructor(
    protected ecartStockService: EcartStockService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ecartStockService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ecartStockListModification');
      this.activeModal.close();
    });
  }
}
