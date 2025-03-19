import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFamilleProduit, FamilleProduit } from 'app/shared/model/ProductInventory/famille-produit.model';
import { FamilleProduitService } from './famille-produit.service';

@Component({
  selector: 'jhi-famille-produit-update',
  templateUrl: './famille-produit-update.component.html',
})
export class FamilleProduitUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    description: [],
    options: [],
  });

  constructor(protected familleProduitService: FamilleProduitService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ familleProduit }) => {
      this.updateForm(familleProduit);
    });
  }

  updateForm(familleProduit: IFamilleProduit): void {
    this.editForm.patchValue({
      id: familleProduit.id,
      nom: familleProduit.nom,
      description: familleProduit.description,
      options: familleProduit.options,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const familleProduit = this.createFromForm();
    if (familleProduit.id !== undefined) {
      this.subscribeToSaveResponse(this.familleProduitService.update(familleProduit));
    } else {
      this.subscribeToSaveResponse(this.familleProduitService.create(familleProduit));
    }
  }

  private createFromForm(): IFamilleProduit {
    return {
      ...new FamilleProduit(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      description: this.editForm.get(['description'])!.value,
      options: this.editForm.get(['options'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFamilleProduit>>): void {
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
