import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPointDeVente } from 'app/shared/model/ClientSales/point-de-vente.model';
import { PointDeVenteService } from './point-de-vente.service';

@Component({
  templateUrl: './point-de-vente-delete-dialog.component.html',
})
export class PointDeVenteDeleteDialogComponent {
  pointDeVente?: IPointDeVente;

  constructor(
    protected pointDeVenteService: PointDeVenteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pointDeVenteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pointDeVenteListModification');
      this.activeModal.close();
    });
  }
}
