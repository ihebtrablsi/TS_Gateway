import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEcartStock } from 'app/shared/model/ProductInventory/ecart-stock.model';

@Component({
  selector: 'jhi-ecart-stock-detail',
  templateUrl: './ecart-stock-detail.component.html',
})
export class EcartStockDetailComponent implements OnInit {
  ecartStock: IEcartStock | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ecartStock }) => (this.ecartStock = ecartStock));
  }

  previousState(): void {
    window.history.back();
  }
}
