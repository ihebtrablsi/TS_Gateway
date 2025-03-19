import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPanierBonus } from 'app/shared/model/ClientSales/panier-bonus.model';
import { PanierBonusService } from './panier-bonus.service';

@Component({
  templateUrl: './panier-bonus-delete-dialog.component.html',
})
export class PanierBonusDeleteDialogComponent {
  panierBonus?: IPanierBonus;

  constructor(
    protected panierBonusService: PanierBonusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.panierBonusService.delete(id).subscribe(() => {
      this.eventManager.broadcast('panierBonusListModification');
      this.activeModal.close();
    });
  }
}
