import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRistourne } from 'app/shared/model/Finance/ristourne.model';
import { RistourneService } from './ristourne.service';

@Component({
  templateUrl: './ristourne-delete-dialog.component.html',
})
export class RistourneDeleteDialogComponent {
  ristourne?: IRistourne;

  constructor(protected ristourneService: RistourneService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ristourneService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ristourneListModification');
      this.activeModal.close();
    });
  }
}
