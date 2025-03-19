import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISuiviAction, SuiviAction } from 'app/shared/model/ClientSales/suivi-action.model';
import { SuiviActionService } from './suivi-action.service';
import { IActionCommerciale } from 'app/shared/model/ClientSales/action-commerciale.model';
import { ActionCommercialeService } from 'app/entities/ClientSales/action-commerciale/action-commerciale.service';

@Component({
  selector: 'jhi-suivi-action-update',
  templateUrl: './suivi-action-update.component.html',
})
export class SuiviActionUpdateComponent implements OnInit {
  isSaving = false;
  actioncommerciales: IActionCommerciale[] = [];

  editForm = this.fb.group({
    id: [],
    dateSuivi: [null, [Validators.required]],
    indicateurs: [null, [Validators.required]],
    commentaire: [],
    actionCommercialeId: [],
  });

  constructor(
    protected suiviActionService: SuiviActionService,
    protected actionCommercialeService: ActionCommercialeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ suiviAction }) => {
      if (!suiviAction.id) {
        const today = moment().startOf('day');
        suiviAction.dateSuivi = today;
      }

      this.updateForm(suiviAction);

      this.actionCommercialeService
        .query()
        .subscribe((res: HttpResponse<IActionCommerciale[]>) => (this.actioncommerciales = res.body || []));
    });
  }

  updateForm(suiviAction: ISuiviAction): void {
    this.editForm.patchValue({
      id: suiviAction.id,
      dateSuivi: suiviAction.dateSuivi ? suiviAction.dateSuivi.format(DATE_TIME_FORMAT) : null,
      indicateurs: suiviAction.indicateurs,
      commentaire: suiviAction.commentaire,
      actionCommercialeId: suiviAction.actionCommercialeId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const suiviAction = this.createFromForm();
    if (suiviAction.id !== undefined) {
      this.subscribeToSaveResponse(this.suiviActionService.update(suiviAction));
    } else {
      this.subscribeToSaveResponse(this.suiviActionService.create(suiviAction));
    }
  }

  private createFromForm(): ISuiviAction {
    return {
      ...new SuiviAction(),
      id: this.editForm.get(['id'])!.value,
      dateSuivi: this.editForm.get(['dateSuivi'])!.value ? moment(this.editForm.get(['dateSuivi'])!.value, DATE_TIME_FORMAT) : undefined,
      indicateurs: this.editForm.get(['indicateurs'])!.value,
      commentaire: this.editForm.get(['commentaire'])!.value,
      actionCommercialeId: this.editForm.get(['actionCommercialeId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISuiviAction>>): void {
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

  trackById(index: number, item: IActionCommerciale): any {
    return item.id;
  }
}
