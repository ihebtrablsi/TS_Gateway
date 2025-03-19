import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIncentive } from 'app/shared/model/Finance/incentive.model';
import { IncentiveService } from './incentive.service';

@Component({
  templateUrl: './incentive-delete-dialog.component.html',
})
export class IncentiveDeleteDialogComponent {
  incentive?: IIncentive;

  constructor(protected incentiveService: IncentiveService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.incentiveService.delete(id).subscribe(() => {
      this.eventManager.broadcast('incentiveListModification');
      this.activeModal.close();
    });
  }
}
