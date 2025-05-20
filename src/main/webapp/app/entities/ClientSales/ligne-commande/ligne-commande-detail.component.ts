import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILigneCommande } from 'app/shared/model/ClientSales/ligne-commande.model';
import { CommandeService } from 'app/entities/ClientSales/commande/commande.service';

@Component({
  selector: 'jhi-ligne-commande-detail',
  templateUrl: './ligne-commande-detail.component.html',
})
export class LigneCommandeDetailComponent implements OnInit {
  ligneCommande: ILigneCommande | null = null;
  commandeTotal: number | string = 'Inconnu';

  constructor(protected activatedRoute: ActivatedRoute, private commandeService: CommandeService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ligneCommande }) => {
      this.ligneCommande = ligneCommande;
      if (ligneCommande.commandeId) {
        this.commandeService.find(ligneCommande.commandeId).subscribe(res => {
          this.commandeTotal = res.body?.total || 'Inconnu';
        });
      }
    });
  }
  previousState(): void {
    window.history.back();
  }
}
