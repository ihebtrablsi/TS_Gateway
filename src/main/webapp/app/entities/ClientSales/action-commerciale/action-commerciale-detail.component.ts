import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IActionCommerciale } from 'app/shared/model/ClientSales/action-commerciale.model';

@Component({
  selector: 'jhi-action-commerciale-detail',
  templateUrl: './action-commerciale-detail.component.html',
})
export class ActionCommercialeDetailComponent implements OnInit {
  actionCommerciale: IActionCommerciale | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ actionCommerciale }) => (this.actionCommerciale = actionCommerciale));
  }

  previousState(): void {
    window.history.back();
  }
}
