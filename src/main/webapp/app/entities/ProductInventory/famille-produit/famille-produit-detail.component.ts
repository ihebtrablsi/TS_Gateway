import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFamilleProduit } from 'app/shared/model/ProductInventory/famille-produit.model';

@Component({
  selector: 'jhi-famille-produit-detail',
  templateUrl: './famille-produit-detail.component.html',
})
export class FamilleProduitDetailComponent implements OnInit {
  familleProduit: IFamilleProduit | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ familleProduit }) => (this.familleProduit = familleProduit));
  }

  previousState(): void {
    window.history.back();
  }
}
