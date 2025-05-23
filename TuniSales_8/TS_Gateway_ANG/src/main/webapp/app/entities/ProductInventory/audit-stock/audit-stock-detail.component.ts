import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAuditStock } from 'app/shared/model/ProductInventory/audit-stock.model';
import { DepotService } from 'app/entities/ProductInventory/depot/depot.service';

@Component({
  selector: 'jhi-audit-stock-detail',
  templateUrl: './audit-stock-detail.component.html',
})
export class AuditStockDetailComponent implements OnInit {
  auditStock: IAuditStock | null = null;
  depotNom = 'Inconnu';

  constructor(protected activatedRoute: ActivatedRoute, protected depotService: DepotService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ auditStock }) => {
      this.auditStock = auditStock;
      if (auditStock?.depotId) {
        this.depotService.find(auditStock.depotId).subscribe(res => {
          this.depotNom = res.body?.nom ?? 'Inconnu';
        });
      }
    });
  }

  previousState(): void {
    window.history.back();
  }
}
