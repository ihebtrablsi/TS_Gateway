import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPanierBonus } from 'app/shared/model/ClientSales/panier-bonus.model';

@Component({
  selector: 'jhi-panier-bonus-detail',
  templateUrl: './panier-bonus-detail.component.html',
})
export class PanierBonusDetailComponent implements OnInit {
  panierBonus: IPanierBonus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ panierBonus }) => (this.panierBonus = panierBonus));
  }

  previousState(): void {
    window.history.back();
  }
}
