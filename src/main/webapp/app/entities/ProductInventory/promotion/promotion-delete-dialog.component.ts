import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPromotion } from 'app/shared/model/ProductInventory/promotion.model';
import { PromotionService } from './promotion.service';

@Component({
  templateUrl: './promotion-delete-dialog.component.html',
})
export class PromotionDeleteDialogComponent {
  promotion?: IPromotion;

  constructor(protected promotionService: PromotionService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.promotionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('promotionListModification');
      this.activeModal.close();
    });
  }
}
