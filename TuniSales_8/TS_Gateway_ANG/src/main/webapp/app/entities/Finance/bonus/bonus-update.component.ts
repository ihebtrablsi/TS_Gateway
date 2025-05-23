import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IBonus, Bonus } from 'app/shared/model/Finance/bonus.model';
import { BonusService } from './bonus.service';
import { IIncentive } from 'app/shared/model/Finance/incentive.model';
import { IncentiveService } from 'app/entities/Finance/incentive/incentive.service';

@Component({
  selector: 'jhi-bonus-update',
  templateUrl: './bonus-update.component.html',
})
export class BonusUpdateComponent implements OnInit {
  isSaving = false;
  incentives: IIncentive[] = [];

  editForm = this.fb.group({
    id: [],
    montant: [null, [Validators.required, Validators.min(0)]],
    dateAttribution: [null, [Validators.required]],
    statut: [null, [Validators.required]],
    incentiveId: [],
  });

  constructor(
    protected bonusService: BonusService,
    protected incentiveService: IncentiveService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bonus }) => {
      if (!bonus.id) {
        const today = moment().startOf('day');
        bonus.dateAttribution = today;
      }

      this.updateForm(bonus);

      this.incentiveService.query().subscribe((res: HttpResponse<IIncentive[]>) => (this.incentives = res.body || []));
    });
  }

  updateForm(bonus: IBonus): void {
    this.editForm.patchValue({
      id: bonus.id,
      montant: bonus.montant,
      dateAttribution: bonus.dateAttribution ? bonus.dateAttribution.format(DATE_TIME_FORMAT) : null,
      statut: bonus.statut,
      incentiveId: bonus.incentiveId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bonus = this.createFromForm();
    if (bonus.id !== undefined) {
      this.subscribeToSaveResponse(this.bonusService.update(bonus));
    } else {
      this.subscribeToSaveResponse(this.bonusService.create(bonus));
    }
  }

  private createFromForm(): IBonus {
    return {
      ...new Bonus(),
      id: this.editForm.get(['id'])!.value,
      montant: this.editForm.get(['montant'])!.value,
      dateAttribution: this.editForm.get(['dateAttribution'])!.value
        ? moment(this.editForm.get(['dateAttribution'])!.value, DATE_TIME_FORMAT)
        : undefined,
      statut: this.editForm.get(['statut'])!.value,
      incentiveId: this.editForm.get(['incentiveId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBonus>>): void {
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

  trackById(index: number, item: IIncentive): any {
    return item.id;
  }
}
