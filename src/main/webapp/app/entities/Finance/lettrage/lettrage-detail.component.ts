import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILettrage } from 'app/shared/model/Finance/lettrage.model';
import { FactureService } from 'app/entities/Finance/facture/facture.service';
import { PaiementService } from 'app/entities/Finance/paiement/paiement.service';

@Component({
  selector: 'jhi-lettrage-detail',
  templateUrl: './lettrage-detail.component.html',
})
export class LettrageDetailComponent implements OnInit {
  lettrage: ILettrage | null = null;
  factureNumero = 'Inconnu';
  paiementReference = 'Inconnu';
  constructor(protected activatedRoute: ActivatedRoute, private factureService: FactureService, private paiementService: PaiementService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lettrage }) => {
      this.lettrage = lettrage;

      if (lettrage?.factureId) {
        this.factureService.find(lettrage.factureId).subscribe(res => {
          this.factureNumero = res.body?.numeroFacture ?? 'Inconnu';
        });
      }

      if (lettrage?.paiementId) {
        this.paiementService.find(lettrage.paiementId).subscribe(res => {
          this.paiementReference = res.body?.reference ?? 'Inconnu';
        });
      }
    });
  }

  previousState(): void {
    window.history.back();
  }
}
