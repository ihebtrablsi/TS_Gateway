import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAuditStock } from 'app/shared/model/ProductInventory/audit-stock.model';

@Component({
  selector: 'jhi-audit-stock-detail',
  templateUrl: './audit-stock-detail.component.html',
})
export class AuditStockDetailComponent implements OnInit {
  auditStock: IAuditStock | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ auditStock }) => (this.auditStock = auditStock));
  }

  previousState(): void {
    window.history.back();
  }
}
