import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IIncentive, Incentive } from 'app/shared/model/Finance/incentive.model';
import { IncentiveService } from './incentive.service';

@Component({
  selector: 'jhi-incentive-update',
  templateUrl: './incentive-update.component.html',
})
export class IncentiveUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    description: [],
    dateDebut: [null, [Validators.required]],
    dateFin: [null, [Validators.required]],
    regles: [null, [Validators.required]],
    bonus: [null, [Validators.required]],
  });

  constructor(protected incentiveService: IncentiveService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ incentive }) => {
      if (!incentive.id) {
        const today = moment().startOf('day');
        incentive.dateDebut = today;
        incentive.dateFin = today;
      }

      this.updateForm(incentive);
    });
  }

  updateForm(incentive: IIncentive): void {
    this.editForm.patchValue({
      id: incentive.id,
      nom: incentive.nom,
      description: incentive.description,
      dateDebut: incentive.dateDebut ? incentive.dateDebut.format(DATE_TIME_FORMAT) : null,
      dateFin: incentive.dateFin ? incentive.dateFin.format(DATE_TIME_FORMAT) : null,
      regles: incentive.regles,
      bonus: incentive.bonus,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const incentive = this.createFromForm();
    if (incentive.id !== undefined) {
      this.subscribeToSaveResponse(this.incentiveService.update(incentive));
    } else {
      this.subscribeToSaveResponse(this.incentiveService.create(incentive));
    }
  }

  private createFromForm(): IIncentive {
    return {
      ...new Incentive(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      description: this.editForm.get(['description'])!.value,
      dateDebut: this.editForm.get(['dateDebut'])!.value ? moment(this.editForm.get(['dateDebut'])!.value, DATE_TIME_FORMAT) : undefined,
      dateFin: this.editForm.get(['dateFin'])!.value ? moment(this.editForm.get(['dateFin'])!.value, DATE_TIME_FORMAT) : undefined,
      regles: this.editForm.get(['regles'])!.value,
      bonus: this.editForm.get(['bonus'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIncentive>>): void {
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
