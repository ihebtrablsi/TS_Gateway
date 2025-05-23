import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPanierBonus, PanierBonus } from 'app/shared/model/ClientSales/panier-bonus.model';
import { PanierBonusService } from './panier-bonus.service';

@Component({
  selector: 'jhi-panier-bonus-update',
  templateUrl: './panier-bonus-update.component.html',
})
export class PanierBonusUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    totalPoints: [null, [Validators.required, Validators.min(0)]],
    totalValeur: [null, [Validators.required, Validators.min(0)]],
  });

  constructor(protected panierBonusService: PanierBonusService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ panierBonus }) => {
      this.updateForm(panierBonus);
    });
  }

  updateForm(panierBonus: IPanierBonus): void {
    this.editForm.patchValue({
      id: panierBonus.id,
      totalPoints: panierBonus.totalPoints,
      totalValeur: panierBonus.totalValeur,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const panierBonus = this.createFromForm();
    if (panierBonus.id !== undefined) {
      this.subscribeToSaveResponse(this.panierBonusService.update(panierBonus));
    } else {
      this.subscribeToSaveResponse(this.panierBonusService.create(panierBonus));
    }
  }

  private createFromForm(): IPanierBonus {
    return {
      ...new PanierBonus(),
      id: this.editForm.get(['id'])!.value,
      totalPoints: this.editForm.get(['totalPoints'])!.value,
      totalValeur: this.editForm.get(['totalValeur'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPanierBonus>>): void {
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
}
