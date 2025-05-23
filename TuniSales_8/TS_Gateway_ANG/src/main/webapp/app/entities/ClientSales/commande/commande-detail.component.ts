import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommande } from 'app/shared/model/ClientSales/commande.model';
import { ClientService } from 'app/entities/ClientSales/client/client.service';
import { PointDeVenteService } from 'app/entities/ClientSales/point-de-vente/point-de-vente.service';

@Component({
  selector: 'jhi-commande-detail',
  templateUrl: './commande-detail.component.html',
})
export class CommandeDetailComponent implements OnInit {
  commande: ICommande | null = null;

  clientNom = 'Inconnu';
  pointDeVenteNom = 'Inconnu';

  constructor(
    protected activatedRoute: ActivatedRoute,
    private clientService: ClientService,
    private pointDeVenteService: PointDeVenteService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commande }) => {
      this.commande = commande;

      if (commande.clientId) {
        this.clientService.find(commande.clientId).subscribe(res => {
          this.clientNom = res.body?.nom || 'Inconnu';
        });
      }

      if (commande.pointDeVenteId) {
        this.pointDeVenteService.find(commande.pointDeVenteId).subscribe(res => {
          this.pointDeVenteNom = res.body?.nom || 'Inconnu';
        });
      }
    });
  }

  previousState(): void {
    window.history.back();
  }
}
