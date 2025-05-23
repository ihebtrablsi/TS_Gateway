import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICode } from 'app/shared/model/ProductInventory/code.model';
import { ProduitService } from 'app/entities/ProductInventory/produit/produit.service';

@Component({
  selector: 'jhi-code-detail',
  templateUrl: './code-detail.component.html',
})
export class CodeDetailComponent implements OnInit {
  code: ICode | null = null;
  ProduitNom = 'Inconnu';

  constructor(protected activatedRoute: ActivatedRoute, private produitService: ProduitService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ code }) => {
      this.code = code;
      if (code.produitId) {
        this.produitService.find(code.produitId).subscribe(res => {
          this.ProduitNom = res.body?.nom || 'Inconnu';
        });
      }
    });
  }
  previousState(): void {
    window.history.back();
  }
}
