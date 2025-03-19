import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFamilleProduit } from 'app/shared/model/ProductInventory/famille-produit.model';
import { FamilleProduitService } from './famille-produit.service';

@Component({
  templateUrl: './famille-produit-delete-dialog.component.html',
})
export class FamilleProduitDeleteDialogComponent {
  familleProduit?: IFamilleProduit;

  constructor(
    protected familleProduitService: FamilleProduitService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.familleProduitService.delete(id).subscribe(() => {
      this.eventManager.broadcast('familleProduitListModification');
      this.activeModal.close();
    });
  }
}
