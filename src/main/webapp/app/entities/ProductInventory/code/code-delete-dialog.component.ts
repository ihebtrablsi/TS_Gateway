import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICode } from 'app/shared/model/ProductInventory/code.model';
import { CodeService } from './code.service';

@Component({
  templateUrl: './code-delete-dialog.component.html',
})
export class CodeDeleteDialogComponent {
  code?: ICode;

  constructor(protected codeService: CodeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.codeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('codeListModification');
      this.activeModal.close();
    });
  }
}
