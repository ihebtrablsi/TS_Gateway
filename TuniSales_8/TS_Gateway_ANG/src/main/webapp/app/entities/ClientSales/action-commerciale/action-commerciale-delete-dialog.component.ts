import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IActionCommerciale } from 'app/shared/model/ClientSales/action-commerciale.model';
import { ActionCommercialeService } from './action-commerciale.service';

@Component({
  templateUrl: './action-commerciale-delete-dialog.component.html',
})
export class ActionCommercialeDeleteDialogComponent {
  actionCommerciale?: IActionCommerciale;

  constructor(
    protected actionCommercialeService: ActionCommercialeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.actionCommercialeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('actionCommercialeListModification');
      this.activeModal.close();
    });
  }
}
