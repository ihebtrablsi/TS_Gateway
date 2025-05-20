import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IActionCommerciale } from 'app/shared/model/ClientSales/action-commerciale.model';
import { PointDeVenteService } from 'app/entities/ClientSales/point-de-vente/point-de-vente.service';

@Component({
  selector: 'jhi-action-commerciale-detail',
  templateUrl: './action-commerciale-detail.component.html',
})
export class ActionCommercialeDetailComponent implements OnInit {
  actionCommerciale: IActionCommerciale | null = null;
  pointDeVenteNom = 'Inconnu';

  constructor(protected activatedRoute: ActivatedRoute, private pointDeVenteService: PointDeVenteService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ actionCommerciale }) => {
      this.actionCommerciale = actionCommerciale;
      if (actionCommerciale.pointDeVenteId) {
        this.pointDeVenteService.find(actionCommerciale.pointDeVenteId).subscribe(res => {
          this.pointDeVenteNom = res.body?.nom || 'Inconnu';
        });
      }
    });
  }

  previousState(): void {
    window.history.back();
  }
}
