import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPromotion, Promotion } from 'app/shared/model/ProductInventory/promotion.model';
import { PromotionService } from './promotion.service';
import { IProduit } from 'app/shared/model/ProductInventory/produit.model';
import { ProduitService } from 'app/entities/ProductInventory/produit/produit.service';

@Component({
  selector: 'jhi-promotion-update',
  templateUrl: './promotion-update.component.html',
})
export class PromotionUpdateComponent implements OnInit {
  isSaving = false;
  produits: IProduit[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    dateDebut: [null, [Validators.required]],
    dateFin: [null, [Validators.required]],
    remise: [null, [Validators.required, Validators.min(0)]],
    produitId: [],
  });

  constructor(
    protected promotionService: PromotionService,
    protected produitService: ProduitService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ promotion }) => {
      if (!promotion.id) {
        const today = moment().startOf('day');
        promotion.dateDebut = today;
        promotion.dateFin = today;
      }

      this.updateForm(promotion);

      this.produitService.query().subscribe((res: HttpResponse<IProduit[]>) => (this.produits = res.body || []));
    });
  }

  updateForm(promotion: IPromotion): void {
    this.editForm.patchValue({
      id: promotion.id,
      nom: promotion.nom,
      dateDebut: promotion.dateDebut ? promotion.dateDebut.format(DATE_TIME_FORMAT) : null,
      dateFin: promotion.dateFin ? promotion.dateFin.format(DATE_TIME_FORMAT) : null,
      remise: promotion.remise,
      produitId: promotion.produitId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const promotion = this.createFromForm();
    if (promotion.id !== undefined) {
      this.subscribeToSaveResponse(this.promotionService.update(promotion));
    } else {
      this.subscribeToSaveResponse(this.promotionService.create(promotion));
    }
  }

  private createFromForm(): IPromotion {
    return {
      ...new Promotion(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      dateDebut: this.editForm.get(['dateDebut'])!.value ? moment(this.editForm.get(['dateDebut'])!.value, DATE_TIME_FORMAT) : undefined,
      dateFin: this.editForm.get(['dateFin'])!.value ? moment(this.editForm.get(['dateFin'])!.value, DATE_TIME_FORMAT) : undefined,
      remise: this.editForm.get(['remise'])!.value,
      produitId: this.editForm.get(['produitId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPromotion>>): void {
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

  trackById(index: number, item: IProduit): any {
    return item.id;
  }
}
