import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProduit } from 'app/shared/model/ProductInventory/produit.model';
import { FamilleProduitService } from 'app/entities/ProductInventory/famille-produit/famille-produit.service';

@Component({
  selector: 'jhi-produit-detail',
  templateUrl: './produit-detail.component.html',
})
export class ProduitDetailComponent implements OnInit {
  produit: IProduit | null = null;
  FamilleProduitNom = 'Inconnu';

  constructor(protected activatedRoute: ActivatedRoute, private familleProduitService: FamilleProduitService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ produit }) => {
      this.produit = produit;
      if (produit.familleId) {
        this.familleProduitService.find(produit.familleId).subscribe(res => {
          this.FamilleProduitNom = res.body?.nom || 'Inconnu';
        });
      }
    });
  }
  previousState(): void {
    window.history.back();
  }
}
