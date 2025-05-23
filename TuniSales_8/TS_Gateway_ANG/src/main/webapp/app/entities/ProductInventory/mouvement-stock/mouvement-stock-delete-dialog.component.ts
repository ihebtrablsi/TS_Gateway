import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMouvementStock } from 'app/shared/model/ProductInventory/mouvement-stock.model';
import { MouvementStockService } from './mouvement-stock.service';

@Component({
  templateUrl: './mouvement-stock-delete-dialog.component.html',
})
export class MouvementStockDeleteDialogComponent {
  mouvementStock?: IMouvementStock;

  constructor(
    protected mouvementStockService: MouvementStockService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mouvementStockService.delete(id).subscribe(() => {
      this.eventManager.broadcast('mouvementStockListModification');
      this.activeModal.close();
    });
  }
}
