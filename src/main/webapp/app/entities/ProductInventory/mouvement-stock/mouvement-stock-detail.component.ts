import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMouvementStock } from 'app/shared/model/ProductInventory/mouvement-stock.model';

@Component({
  selector: 'jhi-mouvement-stock-detail',
  templateUrl: './mouvement-stock-detail.component.html',
})
export class MouvementStockDetailComponent implements OnInit {
  mouvementStock: IMouvementStock | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mouvementStock }) => (this.mouvementStock = mouvementStock));
  }

  previousState(): void {
    window.history.back();
  }
}
