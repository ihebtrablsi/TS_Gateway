import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPromotion } from 'app/shared/model/ProductInventory/promotion.model';
import { ProduitService } from 'app/entities/ProductInventory/produit/produit.service';

@Component({
  selector: 'jhi-promotion-detail',
  templateUrl: './promotion-detail.component.html',
})
export class PromotionDetailComponent implements OnInit {
  promotion: IPromotion | null = null;
  ProduitNom = 'Inconnu';

  constructor(protected activatedRoute: ActivatedRoute, private produitService: ProduitService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ promotion }) => {
      this.promotion = promotion;
      if (promotion.produitId) {
        this.produitService.find(promotion.produitId).subscribe(res => {
          this.ProduitNom = res.body?.nom || 'Inconnu';
        });
      }
    });
  }
  previousState(): void {
    window.history.back();
  }
}
