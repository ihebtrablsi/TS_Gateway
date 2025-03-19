import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISuiviAction } from 'app/shared/model/ClientSales/suivi-action.model';

@Component({
  selector: 'jhi-suivi-action-detail',
  templateUrl: './suivi-action-detail.component.html',
})
export class SuiviActionDetailComponent implements OnInit {
  suiviAction: ISuiviAction | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ suiviAction }) => (this.suiviAction = suiviAction));
  }

  previousState(): void {
    window.history.back();
  }
}
