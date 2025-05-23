import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFacture, Facture } from 'app/shared/model/Finance/facture.model';
import { FactureService } from './facture.service';

@Component({
  selector: 'jhi-facture-update',
  templateUrl: './facture-update.component.html',
})
export class FactureUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    numeroFacture: [null, [Validators.required]],
    dateFacture: [null, [Validators.required]],
    montantTotal: [null, [Validators.required, Validators.min(0)]],
    statut: [null, [Validators.required]],
    dateEcheance: [],
  });

  constructor(protected factureService: FactureService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ facture }) => {
      if (!facture.id) {
        const today = moment().startOf('day');
        facture.dateFacture = today;
        facture.dateEcheance = today;
      }

      this.updateForm(facture);
    });
  }

  updateForm(facture: IFacture): void {
    this.editForm.patchValue({
      id: facture.id,
      numeroFacture: facture.numeroFacture,
      dateFacture: facture.dateFacture ? facture.dateFacture.format(DATE_TIME_FORMAT) : null,
      montantTotal: facture.montantTotal,
      statut: facture.statut,
      dateEcheance: facture.dateEcheance ? facture.dateEcheance.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const facture = this.createFromForm();
    if (facture.id !== undefined) {
      this.subscribeToSaveResponse(this.factureService.update(facture));
    } else {
      this.subscribeToSaveResponse(this.factureService.create(facture));
    }
  }

  private createFromForm(): IFacture {
    return {
      ...new Facture(),
      id: this.editForm.get(['id'])!.value,
      numeroFacture: this.editForm.get(['numeroFacture'])!.value,
      dateFacture: this.editForm.get(['dateFacture'])!.value
        ? moment(this.editForm.get(['dateFacture'])!.value, DATE_TIME_FORMAT)
        : undefined,
      montantTotal: this.editForm.get(['montantTotal'])!.value,
      statut: this.editForm.get(['statut'])!.value,
      dateEcheance: this.editForm.get(['dateEcheance'])!.value
        ? moment(this.editForm.get(['dateEcheance'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFacture>>): void {
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
