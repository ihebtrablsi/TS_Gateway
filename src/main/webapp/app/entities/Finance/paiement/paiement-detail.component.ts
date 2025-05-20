import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaiement } from 'app/shared/model/Finance/paiement.model';
import { FactureService } from 'app/entities/Finance/facture/facture.service';
import { CompteService } from 'app/entities/Finance/compte/compte.service';

@Component({
  selector: 'jhi-paiement-detail',
  templateUrl: './paiement-detail.component.html',
})
export class PaiementDetailComponent implements OnInit {
  paiement: IPaiement | null = null;
  factureNumero = 'Inconnu';
  compteNumero = 'Inconnu';
  constructor(protected activatedRoute: ActivatedRoute, private factureService: FactureService, private compteService: CompteService) {}
  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paiement }) => {
      this.paiement = paiement;

      if (paiement?.factureId) {
        this.factureService.find(paiement.factureId).subscribe(res => {
          this.factureNumero = res.body?.numeroFacture ?? 'Inconnu';
        });
      }

      if (paiement?.compteId) {
        this.compteService.find(paiement.compteId).subscribe(res => {
          this.compteNumero = res.body?.numeroCompte ?? 'Inconnu';
        });
      }
    });
  }

  previousState(): void {
    window.history.back();
  }
}
