import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISuiviAction } from 'app/shared/model/ClientSales/suivi-action.model';
import { ActionCommercialeService } from 'app/entities/ClientSales/action-commerciale/action-commerciale.service';

@Component({
  selector: 'jhi-suivi-action-detail',
  templateUrl: './suivi-action-detail.component.html',
})
export class SuiviActionDetailComponent implements OnInit {
  suiviAction: ISuiviAction | null = null;
  ActionCommercialeNom = 'Inconnu';

  constructor(protected activatedRoute: ActivatedRoute, private actionCommercialeService: ActionCommercialeService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ suiviAction }) => {
      this.suiviAction = suiviAction;
      if (suiviAction.actionCommercialeId) {
        this.actionCommercialeService.find(suiviAction.actionCommercialeId).subscribe(res => {
          this.ActionCommercialeNom = res.body?.nom || 'Inconnu';
        });
      }
    });
  }

  previousState(): void {
    window.history.back();
  }
}
