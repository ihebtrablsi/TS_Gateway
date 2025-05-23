import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILettrage } from 'app/shared/model/Finance/lettrage.model';
import { LettrageService } from './lettrage.service';

@Component({
  templateUrl: './lettrage-delete-dialog.component.html',
})
export class LettrageDeleteDialogComponent {
  lettrage?: ILettrage;

  constructor(protected lettrageService: LettrageService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.lettrageService.delete(id).subscribe(() => {
      this.eventManager.broadcast('lettrageListModification');
      this.activeModal.close();
    });
  }
}
