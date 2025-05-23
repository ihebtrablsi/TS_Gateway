import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IActionCommerciale, ActionCommerciale } from 'app/shared/model/ClientSales/action-commerciale.model';
import { ActionCommercialeService } from './action-commerciale.service';
import { IPointDeVente } from 'app/shared/model/ClientSales/point-de-vente.model';
import { PointDeVenteService } from 'app/entities/ClientSales/point-de-vente/point-de-vente.service';

@Component({
  selector: 'jhi-action-commerciale-update',
  templateUrl: './action-commerciale-update.component.html',
})
export class ActionCommercialeUpdateComponent implements OnInit {
  isSaving = false;
  pointdeventes: IPointDeVente[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    description: [],
    dateDebut: [null, [Validators.required]],
    dateFin: [null, [Validators.required]],
    typeAction: [null, [Validators.required]],
    statut: [null, [Validators.required]],
    pointDeVenteId: [],
  });

  constructor(
    protected actionCommercialeService: ActionCommercialeService,
    protected pointDeVenteService: PointDeVenteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ actionCommerciale }) => {
      if (!actionCommerciale.id) {
        const today = moment().startOf('day');
        actionCommerciale.dateDebut = today;
        actionCommerciale.dateFin = today;
      }

      this.updateForm(actionCommerciale);

      this.pointDeVenteService.query().subscribe((res: HttpResponse<IPointDeVente[]>) => (this.pointdeventes = res.body || []));
    });
  }

  updateForm(actionCommerciale: IActionCommerciale): void {
    this.editForm.patchValue({
      id: actionCommerciale.id,
      nom: actionCommerciale.nom,
      description: actionCommerciale.description,
      dateDebut: actionCommerciale.dateDebut ? actionCommerciale.dateDebut.format(DATE_TIME_FORMAT) : null,
      dateFin: actionCommerciale.dateFin ? actionCommerciale.dateFin.format(DATE_TIME_FORMAT) : null,
      typeAction: actionCommerciale.typeAction,
      statut: actionCommerciale.statut,
      pointDeVenteId: actionCommerciale.pointDeVenteId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const actionCommerciale = this.createFromForm();
    if (actionCommerciale.id !== undefined) {
      this.subscribeToSaveResponse(this.actionCommercialeService.update(actionCommerciale));
    } else {
      this.subscribeToSaveResponse(this.actionCommercialeService.create(actionCommerciale));
    }
  }

  private createFromForm(): IActionCommerciale {
    return {
      ...new ActionCommerciale(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      description: this.editForm.get(['description'])!.value,
      dateDebut: this.editForm.get(['dateDebut'])!.value ? moment(this.editForm.get(['dateDebut'])!.value, DATE_TIME_FORMAT) : undefined,
      dateFin: this.editForm.get(['dateFin'])!.value ? moment(this.editForm.get(['dateFin'])!.value, DATE_TIME_FORMAT) : undefined,
      typeAction: this.editForm.get(['typeAction'])!.value,
      statut: this.editForm.get(['statut'])!.value,
      pointDeVenteId: this.editForm.get(['pointDeVenteId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActionCommerciale>>): void {
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

  trackById(index: number, item: IPointDeVente): any {
    return item.id;
  }
}
