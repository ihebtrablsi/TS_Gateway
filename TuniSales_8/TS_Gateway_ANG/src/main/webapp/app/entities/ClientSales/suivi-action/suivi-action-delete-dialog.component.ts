import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISuiviAction } from 'app/shared/model/ClientSales/suivi-action.model';
import { SuiviActionService } from './suivi-action.service';

@Component({
  templateUrl: './suivi-action-delete-dialog.component.html',
})
export class SuiviActionDeleteDialogComponent {
  suiviAction?: ISuiviAction;

  constructor(
    protected suiviActionService: SuiviActionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.suiviActionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('suiviActionListModification');
      this.activeModal.close();
    });
  }
}
