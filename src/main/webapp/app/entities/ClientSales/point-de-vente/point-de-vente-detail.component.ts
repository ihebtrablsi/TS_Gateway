import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPointDeVente } from 'app/shared/model/ClientSales/point-de-vente.model';

@Component({
  selector: 'jhi-point-de-vente-detail',
  templateUrl: './point-de-vente-detail.component.html',
})
export class PointDeVenteDetailComponent implements OnInit {
  pointDeVente: IPointDeVente | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pointDeVente }) => (this.pointDeVente = pointDeVente));
  }

  previousState(): void {
    window.history.back();
  }
}
