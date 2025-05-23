import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAuditStock } from 'app/shared/model/ProductInventory/audit-stock.model';
import { AuditStockService } from './audit-stock.service';

@Component({
  templateUrl: './audit-stock-delete-dialog.component.html',
})
export class AuditStockDeleteDialogComponent {
  auditStock?: IAuditStock;

  constructor(
    protected auditStockService: AuditStockService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.auditStockService.delete(id).subscribe(() => {
      this.eventManager.broadcast('auditStockListModification');
      this.activeModal.close();
    });
  }
}
