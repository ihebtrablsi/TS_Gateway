import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IMouvementStock, MouvementStock } from 'app/shared/model/ProductInventory/mouvement-stock.model';
import { MouvementStockService } from './mouvement-stock.service';
import { IProduit } from 'app/shared/model/ProductInventory/produit.model';
import { ProduitService } from 'app/entities/ProductInventory/produit/produit.service';
import { IDepot } from 'app/shared/model/ProductInventory/depot.model';
import { DepotService } from 'app/entities/ProductInventory/depot/depot.service';

type SelectableEntity = IProduit | IDepot;

@Component({
  selector: 'jhi-mouvement-stock-update',
  templateUrl: './mouvement-stock-update.component.html',
})
export class MouvementStockUpdateComponent implements OnInit {
  isSaving = false;
  produits: IProduit[] = [];
  depots: IDepot[] = [];

  editForm = this.fb.group({
    id: [],
    typeMouvement: [null, [Validators.required]],
    quantite: [null, [Validators.required, Validators.min(0)]],
    dateMouvement: [null, [Validators.required]],
    produitId: [],
    depotId: [],
  });

  constructor(
    protected mouvementStockService: MouvementStockService,
    protected produitService: ProduitService,
    protected depotService: DepotService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mouvementStock }) => {
      if (!mouvementStock.id) {
        const today = moment().startOf('day');
        mouvementStock.dateMouvement = today;
      }

      this.updateForm(mouvementStock);

      this.produitService.query().subscribe((res: HttpResponse<IProduit[]>) => (this.produits = res.body || []));

      this.depotService.query().subscribe((res: HttpResponse<IDepot[]>) => (this.depots = res.body || []));
    });
  }

  updateForm(mouvementStock: IMouvementStock): void {
    this.editForm.patchValue({
      id: mouvementStock.id,
      typeMouvement: mouvementStock.typeMouvement,
      quantite: mouvementStock.quantite,
      dateMouvement: mouvementStock.dateMouvement ? mouvementStock.dateMouvement.format(DATE_TIME_FORMAT) : null,
      produitId: mouvementStock.produitId,
      depotId: mouvementStock.depotId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mouvementStock = this.createFromForm();
    if (mouvementStock.id !== undefined) {
      this.subscribeToSaveResponse(this.mouvementStockService.update(mouvementStock));
    } else {
      this.subscribeToSaveResponse(this.mouvementStockService.create(mouvementStock));
    }
  }

  private createFromForm(): IMouvementStock {
    return {
      ...new MouvementStock(),
      id: this.editForm.get(['id'])!.value,
      typeMouvement: this.editForm.get(['typeMouvement'])!.value,
      quantite: this.editForm.get(['quantite'])!.value,
      dateMouvement: this.editForm.get(['dateMouvement'])!.value
        ? moment(this.editForm.get(['dateMouvement'])!.value, DATE_TIME_FORMAT)
        : undefined,
      produitId: this.editForm.get(['produitId'])!.value,
      depotId: this.editForm.get(['depotId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMouvementStock>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
