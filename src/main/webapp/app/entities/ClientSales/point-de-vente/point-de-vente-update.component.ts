import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPointDeVente, PointDeVente } from 'app/shared/model/ClientSales/point-de-vente.model';
import { PointDeVenteService } from './point-de-vente.service';

@Component({
  selector: 'jhi-point-de-vente-update',
  templateUrl: './point-de-vente-update.component.html',
})
export class PointDeVenteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    adresse: [null, [Validators.required]],
    ville: [null, [Validators.required]],
    codePostal: [],
    telephone: [],
    latitude: [null, [Validators.required]],
    longitude: [null, [Validators.required]],
  });

  constructor(protected pointDeVenteService: PointDeVenteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pointDeVente }) => {
      this.updateForm(pointDeVente);
    });
  }

  updateForm(pointDeVente: IPointDeVente): void {
    this.editForm.patchValue({
      id: pointDeVente.id,
      nom: pointDeVente.nom,
      adresse: pointDeVente.adresse,
      ville: pointDeVente.ville,
      codePostal: pointDeVente.codePostal,
      telephone: pointDeVente.telephone,
      latitude: pointDeVente.latitude,
      longitude: pointDeVente.longitude,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pointDeVente = this.createFromForm();
    if (pointDeVente.id !== undefined) {
      this.subscribeToSaveResponse(this.pointDeVenteService.update(pointDeVente));
    } else {
      this.subscribeToSaveResponse(this.pointDeVenteService.create(pointDeVente));
    }
  }

  private createFromForm(): IPointDeVente {
    return {
      ...new PointDeVente(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      ville: this.editForm.get(['ville'])!.value,
      codePostal: this.editForm.get(['codePostal'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      latitude: this.editForm.get(['latitude'])!.value,
      longitude: this.editForm.get(['longitude'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPointDeVente>>): void {
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
