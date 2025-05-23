import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMouvementStock } from 'app/shared/model/ProductInventory/mouvement-stock.model';
import { ProduitService } from 'app/entities/ProductInventory/produit/produit.service';
import { DepotService } from 'app/entities/ProductInventory/depot/depot.service';

@Component({
  selector: 'jhi-mouvement-stock-detail',
  templateUrl: './mouvement-stock-detail.component.html',
})
export class MouvementStockDetailComponent implements OnInit {
  mouvementStock: IMouvementStock | null = null;
  produitNom = 'Inconnu';
  depotNom = 'Inconnu';

  constructor(protected activatedRoute: ActivatedRoute, protected produitService: ProduitService, protected depotService: DepotService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mouvementStock }) => {
      this.mouvementStock = mouvementStock;

      if (mouvementStock?.produitId) {
        this.produitService.find(mouvementStock.produitId).subscribe(res => {
          this.produitNom = res.body?.nom ?? 'Inconnu';
        });
      }

      if (mouvementStock?.depotId) {
        this.depotService.find(mouvementStock.depotId).subscribe(res => {
          this.depotNom = res.body?.nom ?? 'Inconnu';
        });
      }
    });
  }

  previousState(): void {
    window.history.back();
  }
}
