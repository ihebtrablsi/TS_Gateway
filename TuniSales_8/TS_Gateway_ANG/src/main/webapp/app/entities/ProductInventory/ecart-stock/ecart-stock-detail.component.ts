import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEcartStock } from 'app/shared/model/ProductInventory/ecart-stock.model';
import { ProduitService } from 'app/entities/ProductInventory/produit/produit.service';
import { AuditStockService } from 'app/entities/ProductInventory/audit-stock/audit-stock.service';

@Component({
  selector: 'jhi-ecart-stock-detail',
  templateUrl: './ecart-stock-detail.component.html',
})
export class EcartStockDetailComponent implements OnInit {
  ecartStock: IEcartStock | null = null;
  produitNom = 'Inconnu';
  auditDate = 'Inconnu';

  constructor(protected activatedRoute: ActivatedRoute, private produitService: ProduitService, private auditService: AuditStockService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ecartStock }) => {
      this.ecartStock = ecartStock;

      if (ecartStock?.produitId) {
        this.produitService.find(ecartStock.produitId).subscribe(res => {
          this.produitNom = res.body?.nom ?? 'Inconnu';
        });
      }

      if (ecartStock?.auditId) {
        this.auditService.find(ecartStock.auditId).subscribe(res => {
          const date = res.body?.dateAudit;
          this.auditDate = date ? date.toDate().toLocaleDateString() : 'Inconnu';
        });
      }
    });
  }

  previousState(): void {
    window.history.back();
  }
}
